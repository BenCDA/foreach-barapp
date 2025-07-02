package com.barapp.controller;

import com.barapp.dto.CocktailSizePriceRequest;
import com.barapp.dto.CocktailSizePriceResponse;
import com.barapp.service.CocktailSizePriceService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cocktail-size-prices")
@RequiredArgsConstructor
public class CocktailSizePriceController {

    private final CocktailSizePriceService service;

    @GetMapping
    public List<CocktailSizePriceResponse> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CocktailSizePriceResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // --- NOUVEAU ENDPOINT ---
    @GetMapping("/by-cocktail/{cocktailId}")
    public List<CocktailSizePriceResponse> getByCocktailId(@PathVariable Long cocktailId) {
        return service.getByCocktailId(cocktailId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BARMAN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CocktailSizePriceResponse create(@Valid @RequestBody CocktailSizePriceRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BARMAN')")
    public CocktailSizePriceResponse update(@PathVariable Long id,
                                            @Valid @RequestBody CocktailSizePriceRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BARMAN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
