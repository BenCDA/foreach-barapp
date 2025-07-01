package com.barapp.service;

import java.util.List;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;

public interface OrderService {
    OrderResponse create(String userEmail, OrderRequest request);
    List<OrderResponse> getByUserEmail(String email);
    List<OrderResponse> getByStatusNotFinished();
    OrderResponse updateStatus(Long orderId, OrderRequest request);
}
