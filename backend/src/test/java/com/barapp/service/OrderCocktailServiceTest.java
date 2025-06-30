package com.barapp.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.model.Cocktail;
import com.barapp.model.Order;
import com.barapp.model.OrderCocktail;
import static com.barapp.model.OrderCocktail.Step.DRESSING;
import com.barapp.model.Size;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.repository.OrderRepository;
import com.barapp.repository.SizeRepository;
import com.barapp.service.impl.OrderCocktailServiceImpl;

public class OrderCocktailServiceTest {

    private OrderCocktailRepository repository;
    private OrderRepository orderRepository;
    private CocktailRepository cocktailRepository;
    private SizeRepository sizeRepository;
    private OrderCocktailService service;

    @BeforeEach
    void setUp() {
        repository = mock(OrderCocktailRepository.class);
        orderRepository = mock(OrderRepository.class);
        cocktailRepository = mock(CocktailRepository.class);
        sizeRepository = mock(SizeRepository.class);
        service = new OrderCocktailServiceImpl(repository, orderRepository, cocktailRepository, sizeRepository);
    }

    @Test
    void create_shouldReturnOrderCocktailResponse_whenValidRequest() {
        OrderCocktailRequest request = new OrderCocktailRequest();
        request.setOrderId(1L);
        request.setCocktailId(2L);
        request.setSizeId(3L);
        request.setStep(DRESSING);

        Order order = new Order(); order.setId(1L);
        Cocktail cocktail = new Cocktail(); cocktail.setId(2L);
        Size size = new Size(); size.setId(3L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(cocktailRepository.findById(2L)).thenReturn(Optional.of(cocktail));
        when(sizeRepository.findById(3L)).thenReturn(Optional.of(size));

        OrderCocktail saved = new OrderCocktail(10L, order, cocktail, size, DRESSING);
        when(repository.save(any(OrderCocktail.class))).thenReturn(saved);

        OrderCocktailResponse response = service.create(request);

        assertNotNull(response);
        assertEquals(1L, response.getOrderId());
        assertEquals(DRESSING, response.getStep());
    }
}
