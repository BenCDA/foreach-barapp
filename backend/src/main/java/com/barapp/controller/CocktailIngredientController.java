package com.barapp.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.barapp.dto.CocktailIngredientRequest;
import com.barapp.dto.CocktailIngredientResponse;
import com.barapp.service.CocktailIngredientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cocktail-ingredients")
@RequiredArgsConstructor
public class CocktailIngredientController {

    private final CocktailIngredientService cocktailIngredientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CocktailIngredientResponse create(@RequestBody CocktailIngredientRequest request) {
        return cocktailIngredientService.create(request);
    }

    @GetMapping
    public List<CocktailIngredientResponse> getAll() {
        return cocktailIngredientService.getAll();
    }

    @GetMapping("/{id}")
    public CocktailIngredientResponse getById(@PathVariable Long id) {
        return cocktailIngredientService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cocktailIngredientService.delete(id);
    }
}
