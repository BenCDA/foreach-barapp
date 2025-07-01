package com.barapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barapp.dto.OrderRequest;       // ← import correct de l'enum
import com.barapp.dto.OrderResponse;
import com.barapp.model.Order.Status;
import com.barapp.repository.OrderRepository;
import com.barapp.repository.UserRepository;
import com.barapp.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;
    private final UserRepository userRepository;

    @Override
    public OrderResponse create(String userEmail, OrderRequest req) {
        var user = userRepository.findByEmail(userEmail)
                                 .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        var order = new com.barapp.model.Order();
        order.setUser(user);
        order.setOrderDate(java.time.LocalDateTime.now());
        order.setStatus(req.getStatus());
        var saved = repo.save(order);
        return OrderResponse.fromEntity(saved);
    }

    @Override
    public List<OrderResponse> getByUserEmail(String email) {
        return repo.findByUserEmail(email).stream()
                   .map(OrderResponse::fromEntity)
                   .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getByStatusNotFinished() {
        // ici on utilise Order.Status
        return repo.findByStatusNot(Status.TERMINEE).stream()
                   .map(OrderResponse::fromEntity)
                   .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateStatus(Long orderId, OrderRequest req) {
        var order = repo.findById(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));
        order.setStatus(req.getStatus());
        var updated = repo.save(order);
        return OrderResponse.fromEntity(updated);
    }
}
