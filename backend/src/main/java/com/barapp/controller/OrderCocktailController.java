// src/main/java/com/barapp/controller/OrderCocktailController.java
package com.barapp.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.service.OrderCocktailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order-cocktails")
@RequiredArgsConstructor
public class OrderCocktailController {

    private final OrderCocktailService service;

    // Pour le barman : lister les lignes d'une commande
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public List<OrderCocktailResponse> getByOrder(@PathVariable Long orderId) {
        return service.getByOrderId(orderId);
    }

    // Pour le barman : mettre à jour le step d'une ligne
    @PatchMapping("/{id}/step")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public OrderCocktailResponse updateStep(
            @PathVariable Long id,
            @Valid @RequestBody OrderCocktailRequest request) {
        return service.updateStep(id, request);
    }
}
