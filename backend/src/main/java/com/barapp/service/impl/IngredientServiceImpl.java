package com.barapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barapp.dto.IngredientRequest;
import com.barapp.dto.IngredientResponse;
import com.barapp.model.Ingredient;
import com.barapp.repository.IngredientRepository;
import com.barapp.service.IngredientService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public IngredientResponse create(IngredientRequest request) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());
        Ingredient saved = ingredientRepository.save(ingredient);
        return new IngredientResponse(saved.getId(), saved.getName());
    }

    @Override
    public List<IngredientResponse> getAll() {
        return ingredientRepository.findAll().stream()
                .map(i -> new IngredientResponse(i.getId(), i.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public IngredientResponse getById(Long id) {
        Ingredient i = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));
        return new IngredientResponse(i.getId(), i.getName());
    }

    @Override
    public void delete(Long id) {
        ingredientRepository.deleteById(id);
    }
}
