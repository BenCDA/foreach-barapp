package com.barapp.service.impl;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.model.OrderCocktail;
import com.barapp.model.OrderCocktail.Step;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.repository.OrderRepository;
import com.barapp.service.OrderCocktailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderCocktailServiceImpl implements OrderCocktailService {

    private final OrderCocktailRepository repo;
    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderCocktailResponse> getByOrderId(Long orderId) {
        return repo.findByOrderId(orderId).stream()
                   .map(OrderCocktailResponse::fromEntity)
                   .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderCocktailResponse updateStep(Long id, OrderCocktailRequest req) {
        // 1. Récupère la ligne de commande
        OrderCocktail oc = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("OrderCocktail introuvable pour id = " + id));

        // 2. Met à jour l'étape
        oc.setStep(req.getStep());
        OrderCocktail updated = repo.save(oc);

        // 3. Si tous les cocktails de la commande sont terminés, passe la commande globale à TERMINEE
        Long orderId = updated.getOrder().getId();
        boolean allDone = repo.findByOrderId(orderId).stream()
                             .allMatch(c -> c.getStep() == Step.TERMINE);
        if (allDone) {
            var order = updated.getOrder();
            order.setStatus(com.barapp.model.Order.Status.TERMINEE);
            orderRepository.save(order);
        }

        // 4. Retourne le DTO
        return OrderCocktailResponse.fromEntity(updated);
    }
}
