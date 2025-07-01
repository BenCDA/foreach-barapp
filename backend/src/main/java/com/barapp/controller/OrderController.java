package com.barapp.controller;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;
import com.barapp.service.OrderService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // CLIENT : crée sa commande
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody OrderRequest req,
                                Authentication auth) {
        return orderService.create(auth.getName(), req);
    }

    // CLIENT : liste ses commandes
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public List<OrderResponse> myOrders(Authentication auth) {
        return orderService.getByUserEmail(auth.getName());
    }

    // BARMAN : liste à traiter
    @GetMapping("/to-treat")
    @PreAuthorize("hasRole('BARMAN')")
    public List<OrderResponse> toTreat() {
        return orderService.getByStatusNotFinished();
    }

    // BARMAN : change statut global
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('BARMAN')")
    public OrderResponse updateStatus(@PathVariable Long id,
                                      @Valid @RequestBody OrderRequest req) {
        return orderService.updateStatus(id, req);
    }
}
