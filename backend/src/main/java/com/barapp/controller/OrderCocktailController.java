package com.barapp.controller;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.service.OrderCocktailService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-cocktails")
@RequiredArgsConstructor
public class OrderCocktailController {

    private final OrderCocktailService service;

    // CLIENT & BARMAN : voir cocktails d’une commande
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('USER','BARMAN')")
    public List<OrderCocktailResponse> byOrder(@PathVariable Long orderId) {
        return service.getByOrderId(orderId);
    }

    // BARMAN : avancer l’étape d’un cocktail
    @PatchMapping("/{id}/step")
    @PreAuthorize("hasRole('BARMAN')")
    public OrderCocktailResponse updateStep(@PathVariable Long id,
                                            @Valid @RequestBody OrderCocktailRequest req) {
        return service.updateStep(id, req);
    }
}
