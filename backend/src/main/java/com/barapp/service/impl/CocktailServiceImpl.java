package com.barapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barapp.dto.CocktailRequest;
import com.barapp.dto.CocktailResponse;
import com.barapp.model.Category;
import com.barapp.model.Cocktail;
import com.barapp.repository.CategoryRepository;
import com.barapp.repository.CocktailRepository;
import com.barapp.service.CocktailService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository cocktailRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CocktailResponse create(CocktailRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Cocktail cocktail = new Cocktail();
        cocktail.setName(request.getName());
        cocktail.setDescription(request.getDescription());
        cocktail.setImageUrl(request.getImageUrl());
        cocktail.setCategory(category);

        Cocktail saved = cocktailRepository.save(cocktail);

        // On passe l'objet Category complet ici
        return new CocktailResponse(
            saved.getId(),
            saved.getName(),
            saved.getDescription(),
            saved.getImageUrl(),
            saved.getCategory()
        );
    }

    @Override
    public List<CocktailResponse> getAll() {
        return cocktailRepository.findAll().stream()
                .map(c -> new CocktailResponse(
                    c.getId(),
                    c.getName(),
                    c.getDescription(),
                    c.getImageUrl(),
                    c.getCategory() // objet Category complet
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CocktailResponse getById(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cocktail not found"));

        return new CocktailResponse(
            cocktail.getId(),
            cocktail.getName(),
            cocktail.getDescription(),
            cocktail.getImageUrl(),
            cocktail.getCategory() // objet Category complet
        );
    }

    @Override
    public void delete(Long id) {
        cocktailRepository.deleteById(id);
    }
}
