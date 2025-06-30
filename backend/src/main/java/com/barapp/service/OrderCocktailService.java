package com.barapp.service;

import java.util.List;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;

public interface OrderCocktailService {
    OrderCocktailResponse create(OrderCocktailRequest request);
    List<OrderCocktailResponse> getByOrderId(Long orderId);
    void updateStep(Long id, OrderCocktailRequest request);
    void delete(Long id);
}
