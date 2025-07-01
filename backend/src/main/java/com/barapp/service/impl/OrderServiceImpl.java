package com.barapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barapp.dto.OrderRequest;       // ← import correct de l'enum
import com.barapp.dto.OrderResponse;
import com.barapp.model.Order.Status;
import com.barapp.repository.OrderRepository;
import com.barapp.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repo;

    @Override
    public OrderResponse create(String userEmail, OrderRequest req) {
        // TODO : implémenter
        throw new UnsupportedOperationException("Not implemented");
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
        // TODO : charger, modifier status (Status.valueOf(req.getStatus())), save, mapper → DTO
        throw new UnsupportedOperationException("Not implemented");
    }
}
