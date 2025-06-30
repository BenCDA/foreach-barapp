package com.barapp.service;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    List<OrderResponse> getAll();
    OrderResponse getById(Long id);
    void updateStatus(Long id, OrderRequest request);
    void delete(Long id);
}
