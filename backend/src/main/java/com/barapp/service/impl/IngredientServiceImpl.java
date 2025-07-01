package com.barapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.barapp.dto.IngredientRequest;
import com.barapp.dto.IngredientResponse;
import com.barapp.repository.IngredientRepository;
import com.barapp.service.IngredientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository repo;

    @Override
    public IngredientResponse create(IngredientRequest req) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<IngredientResponse> getAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public IngredientResponse getById(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public IngredientResponse update(Long id, IngredientRequest req) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
