package com.barapp.service;

import java.util.List;

import com.barapp.dto.IngredientRequest;
import com.barapp.dto.IngredientResponse;

public interface IngredientService {
    IngredientResponse create(IngredientRequest request);
    List<IngredientResponse> getAll();
    IngredientResponse getById(Long id);
    IngredientResponse update(Long id, IngredientRequest request);
    void delete(Long id);
}
