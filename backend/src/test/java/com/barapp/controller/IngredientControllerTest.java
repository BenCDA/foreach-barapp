package com.barapp.controller;

import com.barapp.dto.IngredientRequest;
import com.barapp.dto.IngredientResponse;
import com.barapp.service.IngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngredientController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(com.barapp.TestSecurityConfig.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateIngredient() throws Exception {
        IngredientRequest request = new IngredientRequest();
        request.setName("Lemon");

        IngredientResponse response = new IngredientResponse(1L, "Lemon");

        when(ingredientService.create(any(IngredientRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Lemon"));
    }

    @Test
    public void testGetAllIngredients() throws Exception {
        List<IngredientResponse> ingredients = List.of(
            new IngredientResponse(1L, "Lemon"),
            new IngredientResponse(2L, "Sugar")
        );

        when(ingredientService.getAll()).thenReturn(ingredients);

        mockMvc.perform(get("/api/ingredients"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Lemon"))
            .andExpect(jsonPath("$[1].name").value("Sugar"));
    }
}
