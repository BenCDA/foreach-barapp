package com.barapp.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;
import com.barapp.model.Order;
import static com.barapp.model.Order.Status.ORDERED;
import com.barapp.model.User;
import com.barapp.repository.OrderRepository;
import com.barapp.repository.UserRepository;
import com.barapp.service.impl.OrderServiceImpl;

public class OrderServiceTest {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        orderService = new OrderServiceImpl(orderRepository, userRepository);
    }

    @Test
    void create_shouldReturnOrderResponse_whenValidRequest() {
        User user = new User(1L, "Ben", "ben@test.com", "1234", User.Role.CLIENT);
        OrderRequest request = new OrderRequest();
        request.setUserId(1L);
        request.setStatus(ORDERED);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(orderRepository.save(any(Order.class)))
                .thenReturn(new Order(1L, user, LocalDateTime.now(), ORDERED));

        OrderResponse response = orderService.create(request);

        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals(ORDERED, response.getStatus());
    }
}
