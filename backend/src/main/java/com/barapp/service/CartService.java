package com.barapp.service;

import com.barapp.dto.CartRequest;
import com.barapp.dto.CartResponse;

import java.util.List;

public interface CartService {
    void addToCart(String email, CartRequest request);
    List<CartResponse> getCart(String email);
    void clearCart(String email);
}
