package com.barapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.barapp.dto.CocktailIngredientRequest;
import com.barapp.dto.CocktailIngredientResponse;
import com.barapp.repository.CocktailIngredientRepository;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.IngredientRepository;
import com.barapp.service.CocktailIngredientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CocktailIngredientServiceImpl implements CocktailIngredientService {

    private final CocktailIngredientRepository repo;
    private final CocktailRepository cocktailRepo;
    private final IngredientRepository ingredientRepo;

    @Override
    public CocktailIngredientResponse create(CocktailIngredientRequest req) {
        var entity = new com.barapp.model.CocktailIngredient();
        entity.setCocktail(cocktailRepo.findById(req.getCocktailId())
                                       .orElseThrow(() -> new IllegalArgumentException("Cocktail introuvable")));
        entity.setIngredient(ingredientRepo.findById(req.getIngredientId())
                                         .orElseThrow(() -> new IllegalArgumentException("Ingredient introuvable")));
        entity.setQuantity(req.getQuantity());
        var saved = repo.save(entity);
        return new CocktailIngredientResponse(saved.getId(), saved.getCocktail().getId(),
                                             saved.getIngredient().getId(), saved.getQuantity());
    }

    @Override
    public List<CocktailIngredientResponse> getAll() {
        return repo.findAll().stream()
                   .map(ci -> new CocktailIngredientResponse(ci.getId(), ci.getCocktail().getId(),
                                                          ci.getIngredient().getId(), ci.getQuantity()))
                   .toList();
    }

    @Override
    public CocktailIngredientResponse getById(Long id) {
        var ci = repo.findById(id)
                     .orElseThrow(() -> new IllegalArgumentException("CocktailIngredient introuvable"));
        return new CocktailIngredientResponse(ci.getId(), ci.getCocktail().getId(),
                                             ci.getIngredient().getId(), ci.getQuantity());
    }

    @Override
    public CocktailIngredientResponse update(Long id, CocktailIngredientRequest req) {
        var ci = repo.findById(id)
                     .orElseThrow(() -> new IllegalArgumentException("CocktailIngredient introuvable"));
        ci.setCocktail(cocktailRepo.findById(req.getCocktailId())
                                   .orElseThrow(() -> new IllegalArgumentException("Cocktail introuvable")));
        ci.setIngredient(ingredientRepo.findById(req.getIngredientId())
                                       .orElseThrow(() -> new IllegalArgumentException("Ingredient introuvable")));
        ci.setQuantity(req.getQuantity());
        var updated = repo.save(ci);
        return new CocktailIngredientResponse(updated.getId(), updated.getCocktail().getId(),
                                             updated.getIngredient().getId(), updated.getQuantity());
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
