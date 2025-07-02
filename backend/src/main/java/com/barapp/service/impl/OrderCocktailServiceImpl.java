package com.barapp.service.impl;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.model.Order;
import com.barapp.model.OrderCocktail;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.service.OrderCocktailService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCocktailServiceImpl implements OrderCocktailService {

    private final OrderCocktailRepository repo;

    @Override
    @Transactional(readOnly = true)
    public List<OrderCocktailResponse> getByOrderId(Long orderId) {
        Order dummy = new Order();
        dummy.setId(orderId);
        return repo.findByOrder(dummy)
                   .stream()
                   .map(OrderCocktailResponse::fromEntity)
                   .collect(Collectors.toList());
    }

    @Override
    public OrderCocktailResponse updateStep(Long id, OrderCocktailRequest request) {
        OrderCocktail oc = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Ligne introuvable"));
        oc.setStep(OrderCocktail.Step.valueOf(request.getStep()));
        OrderCocktail saved = repo.save(oc);
        return OrderCocktailResponse.fromEntity(saved);
    }
}
