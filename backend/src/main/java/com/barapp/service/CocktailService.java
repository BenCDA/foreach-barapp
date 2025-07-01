package com.barapp.service;

import java.util.List;

import com.barapp.dto.CocktailRequest;
import com.barapp.dto.CocktailResponse;

public interface CocktailService {
    CocktailResponse create(CocktailRequest request);
    List<CocktailResponse> getAll();
    CocktailResponse getById(Long id);
    CocktailResponse update(Long id, CocktailRequest request);
    void delete(Long id);
}
