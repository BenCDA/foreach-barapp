package com.barapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.barapp.dto.OrderCocktailRequest;
import com.barapp.dto.OrderCocktailResponse;
import com.barapp.service.OrderCocktailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order-cocktails")
@RequiredArgsConstructor
public class OrderCocktailController {

    private final OrderCocktailService orderCocktailService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCocktailResponse create(@RequestBody @Valid OrderCocktailRequest request) {
        return orderCocktailService.create(request);
    }

    @GetMapping("/order/{orderId}")
    public List<OrderCocktailResponse> getByOrder(@PathVariable Long orderId) {
        return orderCocktailService.getByOrderId(orderId);
    }

    @PatchMapping("/{id}/step")
    public void updateStep(@PathVariable Long id, @RequestBody @Valid OrderCocktailRequest request) {
        orderCocktailService.updateStep(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderCocktailService.delete(id);
    }
}
