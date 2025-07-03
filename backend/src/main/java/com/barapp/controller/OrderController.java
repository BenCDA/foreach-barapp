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

    // CLIENT : crée sa commande
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest req,
                                     Authentication auth) {
        return orderService.create(auth.getName(), req);
    }

    // CLIENT : liste ses commandes
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    public List<OrderResponse> myOrders(Authentication auth) {
        return orderService.getByUserEmail(auth.getName());
    }

    // BARMAN : liste à traiter
    @GetMapping("/to-treat")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public List<OrderResponse> toTreat() {
        return orderService.getByStatusNotFinished();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_CLIENT','ROLE_BARMAN')")
    public OrderResponse getById(@PathVariable Long id, Authentication auth) {
        boolean isClient = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"));
        if (isClient) {
            return orderService.getByIdAndUserEmail(id, auth.getName());
        }
        return orderService.getById(id);
    }
    // BARMAN : change statut global
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public OrderResponse updateStatus(@PathVariable Long id,
                                      @Valid @RequestBody StatusRequest req) {
        return orderService.updateStatus(id, req);
    }
}
