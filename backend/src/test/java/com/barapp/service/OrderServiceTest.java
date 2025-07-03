package com.barapp.service;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;
import com.barapp.model.*;
import com.barapp.repository.CartRepository;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.repository.OrderRepository;
import com.barapp.repository.UserRepository;
import com.barapp.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.barapp.model.User.Role.CLIENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private UserRepository userRepository;
    private CartRepository cartRepository;
    private OrderRepository orderRepository;
    private OrderCocktailRepository orderCocktailRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        cartRepository = mock(CartRepository.class);
        orderRepository = mock(OrderRepository.class);
        orderCocktailRepository = mock(OrderCocktailRepository.class);

        orderService = new OrderServiceImpl(userRepository, cartRepository, orderRepository, orderCocktailRepository);
    }

    @Test
    void create_shouldCreateOrderAndClearCart() {
        // Arrange
        User user = new User(1L, "Ben", "ben@test.com", "pwd", CLIENT);
        when(userRepository.findByEmail("ben@test.com")).thenReturn(Optional.of(user));

        Order savedOrder = new Order(1L, user, LocalDateTime.now(), Order.Status.COMMANDEE);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        Cocktail cocktail = new Cocktail(1L, "Mojito", "desc", "img.png", null);
        Size size = new Size(1L, "M");
        Cart cartItem = new Cart(1L, user, cocktail, size);
        when(cartRepository.findByUser(user)).thenReturn(List.of(cartItem));

        OrderCocktail savedLine = new OrderCocktail(1L, savedOrder, cocktail, size, OrderCocktail.Step.PREPARATION);
        when(orderCocktailRepository.save(any(OrderCocktail.class))).thenReturn(savedLine);

        OrderRequest request = new OrderRequest();
        request.setPanierId(1L);

        // Act
        OrderResponse response = orderService.create("ben@test.com", request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getOrderId());
        assertEquals("COMMANDEE", response.getStatus());
        assertEquals(1, response.getCocktails().size());
        assertEquals("Mojito", response.getCocktails().get(0).getCocktailName());
        verify(cartRepository).deleteByUser(user);
    }

    @Test
    void getByIdAndUserEmail_shouldThrowSecurityException_whenEmailMismatch() {
        User user = new User(1L, "Ben", "ben@test.com", "pwd", CLIENT);
        Order order = new Order(2L, user, LocalDateTime.now(), Order.Status.COMMANDEE);
        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));

        assertThrows(SecurityException.class, () -> orderService.getByIdAndUserEmail(2L, "other@test.com"));
    }
}
