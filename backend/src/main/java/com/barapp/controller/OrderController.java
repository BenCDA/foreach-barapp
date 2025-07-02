package com.barapp.controller;

import com.barapp.dto.OrderRequest;
import com.barapp.dto.OrderResponse;
import com.barapp.dto.StatusRequest;
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

    /** CLIENT : passer une commande */
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(
            @Valid @RequestBody OrderRequest request,
            Authentication auth
    ) {
        return orderService.create(auth.getName(), request);
    }

    /** CLIENT : lister ses commandes */
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    public List<OrderResponse> listMyOrders(Authentication auth) {
        return orderService.getByUserEmail(auth.getName());
    }

    /** CLIENT : voir le détail d’une commande */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    public OrderResponse getMyOrder(
            @PathVariable Long id,
            Authentication auth
    ) {
        return orderService.getByIdAndUserEmail(id, auth.getName());
    }

    /** BARMAN : lister les commandes à traiter */
    @GetMapping("/to-treat")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public List<OrderResponse> listToTreat() {
        return orderService.getByStatusNotFinished();
    }

    /** BARMAN : mettre à jour le statut global */
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public OrderResponse updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequest request
    ) {
        return orderService.updateStatus(id, request);
    }
}
