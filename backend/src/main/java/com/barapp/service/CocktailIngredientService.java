package com.barapp.service;

import java.util.List;

import com.barapp.dto.CocktailIngredientRequest;
import com.barapp.dto.CocktailIngredientResponse;

public interface CocktailIngredientService {
    CocktailIngredientResponse create(CocktailIngredientRequest request);
    List<CocktailIngredientResponse> getAll();
    CocktailIngredientResponse getById(Long id);
    CocktailIngredientResponse update(Long id, CocktailIngredientRequest request);
    void delete(Long id);
}
