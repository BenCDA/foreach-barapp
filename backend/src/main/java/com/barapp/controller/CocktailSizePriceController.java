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

import com.barapp.dto.CocktailSizePriceRequest;
import com.barapp.dto.CocktailSizePriceResponse;
import com.barapp.service.CocktailSizePriceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cocktail-size-prices")
@RequiredArgsConstructor
public class CocktailSizePriceController {

    private final CocktailSizePriceService cocktailSizePriceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CocktailSizePriceResponse create(@RequestBody @Valid CocktailSizePriceRequest request) {
        return cocktailSizePriceService.create(request);
    }

    @GetMapping
    public List<CocktailSizePriceResponse> getAll() {
        return cocktailSizePriceService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        cocktailSizePriceService.delete(id);
    }
}
