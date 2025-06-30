package com.barapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;
import com.barapp.model.Order;
import com.barapp.model.User;
import com.barapp.repository.OrderRepository;
import com.barapp.repository.UserRepository;
import com.barapp.service.OrderService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public OrderResponse create(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(request.getStatus());
        order.setOrderDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);
        return new OrderResponse(saved.getId(), user.getId(), saved.getOrderDate(), saved.getStatus());
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll().stream()
                .map(o -> new OrderResponse(o.getId(), o.getUser().getId(), o.getOrderDate(), o.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse getById(Long id) {
        Order o = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return new OrderResponse(o.getId(), o.getUser().getId(), o.getOrderDate(), o.getStatus());
    }

    @Override
    public void updateStatus(Long id, OrderRequest request) {
        Order o = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        o.setStatus(request.getStatus());
        orderRepository.save(o);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
