package com.barapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barapp.dto.CocktailRequest;
import com.barapp.dto.CocktailResponse;
import com.barapp.model.Category;
import com.barapp.repository.CategoryRepository;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.CocktailSizePriceRepository;
import com.barapp.repository.CocktailIngredientRepository;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.repository.CartRepository;
import com.barapp.service.CocktailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository repo;
    private final CategoryRepository categoryRepo;
    private final CocktailSizePriceRepository cocktailSizePriceRepo;
    private final CocktailIngredientRepository cocktailIngredientRepo;
    private final OrderCocktailRepository orderCocktailRepo;
    private final CartRepository cartRepo;

    @Override
    public CocktailResponse create(CocktailRequest req) {
        var cocktail = new com.barapp.model.Cocktail();
        cocktail.setName(req.getName());
        cocktail.setDescription(req.getDescription());
        cocktail.setImageUrl(req.getImageUrl());
        if (req.getCategoryId() != null) {
            var cat = categoryRepo.findById(req.getCategoryId())
                                  .orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable"));
            cocktail.setCategory(cat);
        }
        var saved = repo.save(cocktail);
        return new CocktailResponse(saved.getId(), saved.getName(), saved.getDescription(),
                                    saved.getImageUrl(), saved.getCategory());
    }

    @Override
    public List<CocktailResponse> getAll() {
        return repo.findAll().stream()
                   .map(c -> new CocktailResponse(c.getId(), c.getName(), c.getDescription(),
                                                  c.getImageUrl(), c.getCategory()))
                   .toList();
    }

    @Override
    public List<CocktailResponse> getByCategory(Long categoryId) {
        Category cat = categoryRepo.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable"));
        return repo.findByCategory(cat).stream()
                   .map(c -> new CocktailResponse(c.getId(), c.getName(), c.getDescription(),
                                                  c.getImageUrl(), c.getCategory()))
                   .toList();
    }

    @Override
    public CocktailResponse getById(Long id) {
        var c = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cocktail introuvable"));
        return new CocktailResponse(c.getId(), c.getName(), c.getDescription(),
                                    c.getImageUrl(), c.getCategory());
    }

    @Override
    public CocktailResponse update(Long id, CocktailRequest req) {
        var c = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Cocktail introuvable"));
        c.setName(req.getName());
        c.setDescription(req.getDescription());
        c.setImageUrl(req.getImageUrl());
        if (req.getCategoryId() != null) {
            var cat = categoryRepo.findById(req.getCategoryId())
                                  .orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable"));
            c.setCategory(cat);
        } else {
            c.setCategory(null);
        }
        var updated = repo.save(c);
        return new CocktailResponse(updated.getId(), updated.getName(), updated.getDescription(),
                                    updated.getImageUrl(), updated.getCategory());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Supprime les dépendances avant de supprimer le cocktail
        cocktailSizePriceRepo.deleteAllByCocktailId(id);
        cocktailIngredientRepo.deleteAllByCocktailId(id);
        orderCocktailRepo.deleteAllByCocktailId(id);
        cartRepo.deleteAllByCocktailId(id);
        repo.deleteById(id);
    }
}
