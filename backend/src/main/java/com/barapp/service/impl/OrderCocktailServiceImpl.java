// src/main/java/com/barapp/service/impl/OrderCocktailServiceImpl.java
package com.barapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.model.OrderCocktail;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.service.OrderCocktailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCocktailServiceImpl implements OrderCocktailService {

    private final OrderCocktailRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<OrderCocktailResponse> getByOrderId(Long orderId) {
        return repo.findByOrderId(orderId)
                   .stream()
                   .map(OrderCocktailResponse::fromEntity)
                   .collect(Collectors.toList());
    }

    @Override
    public OrderCocktailResponse updateStep(Long id, OrderCocktailRequest request) {
        OrderCocktail oc = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("OrderCocktail introuvable: " + id));
        oc.setStep(request.getStep());
        OrderCocktail updated = repo.save(oc);
        return OrderCocktailResponse.fromEntity(updated);
    }
}
