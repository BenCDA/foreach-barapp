package com.barapp.service.impl;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.model.OrderCocktail;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.repository.OrderRepository;
import com.barapp.service.OrderCocktailService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderCocktailServiceImpl implements OrderCocktailService {

    private final OrderCocktailRepository repo;
    private final OrderRepository orderRepository;

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
        oc.setStep(req.getStep());
        OrderCocktail updated = repo.save(oc);

        // Vérifie si toute la commande est terminée
        Long orderId = updated.getOrder().getId();
        boolean allDone = repo.findByOrderId(orderId).stream()
                              .allMatch(c -> c.getStep() == OrderCocktail.Step.TERMINE);
        if (allDone) {
            var order = updated.getOrder();
            order.setStatus(com.barapp.model.Order.Status.TERMINEE);
            orderRepository.save(order);
        }

        return OrderCocktailResponse.fromEntity(updated);
    }
}
