package com.barapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.barapp.dto.CocktailIngredientRequest;
import com.barapp.dto.CocktailIngredientResponse;
import com.barapp.repository.CocktailIngredientRepository;
import com.barapp.service.CocktailIngredientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CocktailIngredientServiceImpl implements CocktailIngredientService {

    private final CocktailIngredientRepository repo;

    @Override
    public CocktailIngredientResponse create(CocktailIngredientRequest req) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<CocktailIngredientResponse> getAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public CocktailIngredientResponse getById(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public CocktailIngredientResponse update(Long id, CocktailIngredientRequest req) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
