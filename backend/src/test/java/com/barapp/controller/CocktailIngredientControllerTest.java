package com.barapp.controller;

import com.barapp.dto.CocktailIngredientRequest;
import com.barapp.dto.CocktailIngredientResponse;
import com.barapp.service.CocktailIngredientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CocktailIngredientController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(com.barapp.TestSecurityConfig.class)
public class CocktailIngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CocktailIngredientService cocktailIngredientService;

    @Test
    void shouldReturnCocktailIngredientById() throws Exception {
        CocktailIngredientResponse response = new CocktailIngredientResponse(
                1L, 1L, 2L, "10"
        );

        Mockito.when(cocktailIngredientService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/cocktail-ingredients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.cocktailId", is(1)))
                .andExpect(jsonPath("$.ingredientId", is(2)))
                .andExpect(jsonPath("$.quantity", is("10")));
    }

    @Test
    void shouldCreateCocktailIngredient() throws Exception {
        CocktailIngredientRequest request = new CocktailIngredientRequest();
        request.setCocktailId(1L);
        request.setIngredientId(2L);
        request.setQuantity("10");

        CocktailIngredientResponse response = new CocktailIngredientResponse(
                2L, 1L, 2L, "10"
        );

        Mockito.when(cocktailIngredientService.create(Mockito.any(CocktailIngredientRequest.class))).thenReturn(response);

        String jsonRequest = "{\"cocktailId\":1,\"ingredientId\":2,\"quantity\":\"10\"}";

        mockMvc.perform(post("/api/cocktail-ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.cocktailId", is(1)))
                .andExpect(jsonPath("$.ingredientId", is(2)))
                .andExpect(jsonPath("$.quantity", is("10")));
    }
}
