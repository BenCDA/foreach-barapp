package com.barapp.controller;

import com.barapp.dto.CocktailIngredientRequest;
import com.barapp.dto.CocktailIngredientResponse;
import com.barapp.service.CocktailIngredientService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cocktail-ingredients")
@RequiredArgsConstructor
public class CocktailIngredientController {

    private final CocktailIngredientService service;

    @GetMapping
    public List<CocktailIngredientResponse> list() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CocktailIngredientResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CocktailIngredientResponse create(@Valid @RequestBody CocktailIngredientRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    public CocktailIngredientResponse update(@PathVariable Long id,
                                             @Valid @RequestBody CocktailIngredientRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_BARMAN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
