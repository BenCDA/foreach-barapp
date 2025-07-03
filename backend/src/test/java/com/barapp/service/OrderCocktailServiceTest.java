package com.barapp.service;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.model.Cocktail;
import com.barapp.model.Order;
import com.barapp.model.OrderCocktail;
import com.barapp.model.Size;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.service.impl.OrderCocktailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderCocktailServiceTest {
    private OrderCocktailRepository repo;
    private OrderCocktailService service;

    @BeforeEach
    void setUp() {
        repo = mock(OrderCocktailRepository.class);
        service = new OrderCocktailServiceImpl(repo);
    }

    @Test
    void getByOrderId_shouldReturnResponses() {
        Order order = new Order();
        order.setId(1L);
        Cocktail c = new Cocktail(2L, "Mojito", "d", "i", null);
        Size s = new Size(1L, "M");
        OrderCocktail oc = new OrderCocktail(3L, order, c, s, OrderCocktail.Step.PREPARATION);
        when(repo.findByOrderId(1L)).thenReturn(List.of(oc));

        List<OrderCocktailResponse> responses = service.getByOrderId(1L);

        assertEquals(1, responses.size());
        assertEquals(3L, responses.get(0).getId());
    }

    @Test
    void updateStep_shouldPersistChange() {
        OrderCocktail oc = new OrderCocktail(3L, new Order(), new Cocktail(), new Size(), OrderCocktail.Step.PREPARATION);
        when(repo.findById(3L)).thenReturn(Optional.of(oc));
        when(repo.save(any(OrderCocktail.class))).thenAnswer(i -> i.getArgument(0));
        OrderCocktailRequest req = new OrderCocktailRequest();
        req.setStep(OrderCocktail.Step.SERVI);

        OrderCocktailResponse resp = service.updateStep(3L, req);

        assertEquals("SERVI", resp.getStep());
        verify(repo).save(any(OrderCocktail.class));
    }
}
