package com.barapp.service.impl;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.model.OrderCocktail;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.service.OrderCocktailService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderCocktailServiceImpl implements OrderCocktailService {

    private final OrderCocktailRepository repo;

    @Override
    public List<OrderCocktailResponse> getByOrderId(Long orderId) {
        return repo.findByOrderId(orderId).stream()
                   .map(oc -> OrderCocktailResponse.fromEntity(oc))
                   .collect(Collectors.toList());
    }

    @Override
    public OrderCocktailResponse updateStep(Long id, OrderCocktailRequest req) {
        OrderCocktail oc = repo.findById(id)
                               .orElseThrow(() -> new IllegalArgumentException("OrderCocktail introuvable"));
        // TODO: oc.setStep(req.getStep());
        OrderCocktail updated = repo.save(oc);
        return OrderCocktailResponse.fromEntity(updated);
    }
}
