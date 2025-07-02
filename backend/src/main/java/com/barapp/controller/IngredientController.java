package com.barapp.controller;

import com.barapp.dto.IngredientRequest;
import com.barapp.dto.IngredientResponse;
import com.barapp.service.IngredientService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public List<IngredientResponse> list() {
        return ingredientService.getAll();
    }

    @GetMapping("/{id}")
    public IngredientResponse getById(@PathVariable Long id) {
        return ingredientService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BARMAN')")
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientResponse create(@Valid @RequestBody IngredientRequest req) {
        return ingredientService.create(req);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BARMAN')")
    public IngredientResponse update(@PathVariable Long id,
                                     @Valid @RequestBody IngredientRequest req) {
        return ingredientService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BARMAN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ingredientService.delete(id);
    }
}
