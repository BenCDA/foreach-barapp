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
        var ingredient = new com.barapp.model.Ingredient();
        ingredient.setName(req.getName());
        var saved = repo.save(ingredient);
        return new IngredientResponse(saved.getId(), saved.getName());
    }

    @Override
    public List<IngredientResponse> getAll() {
        return repo.findAll().stream()
                   .map(i -> new IngredientResponse(i.getId(), i.getName()))
                   .toList();
    }

    @Override
    public IngredientResponse getById(Long id) {
        var ingredient = repo.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("Ingredient introuvable"));
        return new IngredientResponse(ingredient.getId(), ingredient.getName());
    }

    @Override
    public IngredientResponse update(Long id, IngredientRequest req) {
        var ingredient = repo.findById(id)
                             .orElseThrow(() -> new IllegalArgumentException("Ingredient introuvable"));
        ingredient.setName(req.getName());
        var updated = repo.save(ingredient);
        return new IngredientResponse(updated.getId(), updated.getName());
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
