package com.barapp.controller;

import com.barapp.dto.CartRequest;
import com.barapp.dto.CartResponse;
import com.barapp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestBody CartRequest request, Authentication auth) {
        cartService.addToCart(auth.getName(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCart(Authentication auth) {
        return ResponseEntity.ok(cartService.getCart(auth.getName()));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(Authentication auth) {
        cartService.clearCart(auth.getName());
        return ResponseEntity.ok().build();
    }
}
