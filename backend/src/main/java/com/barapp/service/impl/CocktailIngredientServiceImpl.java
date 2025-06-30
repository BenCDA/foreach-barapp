package com.barapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barapp.dto.CocktailIngredientRequest;
import com.barapp.dto.CocktailIngredientResponse;
import com.barapp.model.Cocktail;
import com.barapp.model.CocktailIngredient;
import com.barapp.model.Ingredient;
import com.barapp.repository.CocktailIngredientRepository;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.IngredientRepository;
import com.barapp.service.CocktailIngredientService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CocktailIngredientServiceImpl implements CocktailIngredientService {

    private final CocktailIngredientRepository cocktailIngredientRepository;
    private final CocktailRepository cocktailRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public CocktailIngredientResponse create(CocktailIngredientRequest request) {
        Cocktail cocktail = cocktailRepository.findById(request.getCocktailId())
                .orElseThrow(() -> new EntityNotFoundException("Cocktail not found"));

        Ingredient ingredient = ingredientRepository.findById(request.getIngredientId())
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));

        CocktailIngredient ci = new CocktailIngredient();
        ci.setCocktail(cocktail);
        ci.setIngredient(ingredient);
        ci.setQuantity(request.getQuantity());

        CocktailIngredient saved = cocktailIngredientRepository.save(ci);

        return new CocktailIngredientResponse(
                saved.getId(),
                saved.getCocktail().getId(),
                saved.getIngredient().getId(),
                saved.getQuantity()
        );
    }

    @Override
    public List<CocktailIngredientResponse> getAll() {
        return cocktailIngredientRepository.findAll().stream()
                .map(ci -> new CocktailIngredientResponse(
                        ci.getId(),
                        ci.getCocktail().getId(),
                        ci.getIngredient().getId(),
                        ci.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CocktailIngredientResponse getById(Long id) {
        CocktailIngredient ci = cocktailIngredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CocktailIngredient not found"));

        return new CocktailIngredientResponse(
                ci.getId(),
                ci.getCocktail().getId(),
                ci.getIngredient().getId(),
                ci.getQuantity()
        );
    }

    @Override
    public void delete(Long id) {
        cocktailIngredientRepository.deleteById(id);
    }
}
