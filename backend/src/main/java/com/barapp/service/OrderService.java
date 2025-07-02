package com.barapp.service;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;
import com.barapp.dto.StatusRequest;

import java.util.List;

public interface OrderService {
    OrderResponse create(String userEmail, OrderRequest request);
    List<OrderResponse> getByUserEmail(String userEmail);
    OrderResponse getByIdAndUserEmail(Long orderId, String userEmail);
    List<OrderResponse> getByStatusNotFinished();
    OrderResponse updateStatus(Long orderId, StatusRequest statusRequest);
}
