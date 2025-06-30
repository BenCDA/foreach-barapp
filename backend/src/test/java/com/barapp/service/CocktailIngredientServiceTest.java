package com.barapp.service;

import com.barapp.dto.CocktailIngredientRequest;
import com.barapp.dto.CocktailIngredientResponse;
import com.barapp.model.Cocktail;
import com.barapp.model.CocktailIngredient;
import com.barapp.model.Ingredient;
import com.barapp.repository.CocktailIngredientRepository;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.IngredientRepository;
import com.barapp.service.impl.CocktailIngredientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CocktailIngredientServiceTest {

    private CocktailIngredientRepository cocktailIngredientRepository;
    private CocktailRepository cocktailRepository;
    private IngredientRepository ingredientRepository;
    private CocktailIngredientService cocktailIngredientService;

    @BeforeEach
    void setUp() {
        cocktailIngredientRepository = mock(CocktailIngredientRepository.class);
        cocktailRepository = mock(CocktailRepository.class);
        ingredientRepository = mock(IngredientRepository.class);
        cocktailIngredientService = new CocktailIngredientServiceImpl(
                cocktailIngredientRepository, cocktailRepository, ingredientRepository);
    }

    @Test
    void create_shouldReturnCocktailIngredientResponse_whenValidRequest() {
        CocktailIngredientRequest request = new CocktailIngredientRequest();
        request.setCocktailId(1L);
        request.setIngredientId(2L);
        request.setQuantity("5cl");

        Cocktail cocktail = new Cocktail();
        cocktail.setId(1L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);

        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));
        when(ingredientRepository.findById(2L)).thenReturn(Optional.of(ingredient));

        CocktailIngredient saved = new CocktailIngredient(10L, cocktail, ingredient, "5cl");
        when(cocktailIngredientRepository.save(any(CocktailIngredient.class))).thenReturn(saved);

        CocktailIngredientResponse response = cocktailIngredientService.create(request);

        assertNotNull(response);
        assertEquals("5cl", response.getQuantity());
        assertEquals(1L, response.getCocktailId());
        assertEquals(2L, response.getIngredientId());
    }
}
