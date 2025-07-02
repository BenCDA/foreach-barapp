package com.barapp.controller;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.service.OrderCocktailService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-cocktails")
@RequiredArgsConstructor
public class OrderCocktailController {

    private final OrderCocktailService service;

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_CLIENT','ROLE_BARMAN')")
    public List<OrderCocktailResponse> getByOrder(@PathVariable Long orderId) {
        return service.getByOrderId(orderId);
    }

    @PatchMapping("/{id}/step")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public OrderCocktailResponse updateStep(
            @PathVariable Long id,
            @RequestBody OrderCocktailRequest request) {
        return service.updateStep(id, request);
    }
}
