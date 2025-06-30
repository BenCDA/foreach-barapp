package com.barapp.service;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.barapp.dto.IngredientRequest;
import com.barapp.dto.IngredientResponse;
import com.barapp.model.Ingredient;
import com.barapp.repository.IngredientRepository;
import com.barapp.service.impl.IngredientServiceImpl;

public class IngredientServiceTest {

    private IngredientRepository ingredientRepository;
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        ingredientRepository = mock(IngredientRepository.class);
        ingredientService = new IngredientServiceImpl(ingredientRepository);
    }

    @Test
    void create_shouldReturnIngredientResponse_whenValidRequest() {
        IngredientRequest request = new IngredientRequest();
        request.setName("Citron vert");

        Ingredient saved = new Ingredient(1L, "Citron vert");
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(saved);

        IngredientResponse response = ingredientService.create(request);

        assertNotNull(response);
        assertEquals("Citron vert", response.getName());
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoneExist() {
        when(ingredientRepository.findAll()).thenReturn(Collections.emptyList());

        List<IngredientResponse> list = ingredientService.getAll();

        assertTrue(list.isEmpty());
    }
}
