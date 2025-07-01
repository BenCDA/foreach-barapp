package com.barapp.service;

import java.util.List;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;

public interface OrderCocktailService {
    List<OrderCocktailResponse> getByOrderId(Long orderId);
    OrderCocktailResponse updateStep(Long id, OrderCocktailRequest request);
}
