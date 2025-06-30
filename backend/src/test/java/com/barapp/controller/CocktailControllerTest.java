package com.barapp.controller;

import com.barapp.dto.CocktailRequest;
import com.barapp.dto.CocktailResponse;
import com.barapp.model.Category;
import com.barapp.service.CocktailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CocktailController.class)
public class CocktailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CocktailService cocktailService;

    @Test
    void shouldReturnCocktailById() throws Exception {
        Category category = new Category(1L, "Soft Drinks");
        CocktailResponse response = new CocktailResponse(1L, "Cola", "Description", "imageUrl", category);

        Mockito.when(cocktailService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/cocktails/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Cola")))
                .andExpect(jsonPath("$.category.name", is("Soft Drinks")));
    }

    @Test
    void shouldCreateCocktail() throws Exception {
        Category category = new Category(1L, "Soft Drinks");

        CocktailRequest request = new CocktailRequest();
        request.setName("Cola");
        request.setDescription("Description");
        request.setImageUrl("imageUrl");
        request.setCategoryId(category.getId());  // Long pour la requête

        CocktailResponse response = new CocktailResponse(2L, "Cola", "Description", "imageUrl", category);

        Mockito.when(cocktailService.create(Mockito.any(CocktailRequest.class))).thenReturn(response);

        String jsonRequest = "{\"name\":\"Cola\",\"description\":\"Description\",\"imageUrl\":\"imageUrl\",\"categoryId\":1}";

        mockMvc.perform(post("/api/cocktails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Cola")))
                .andExpect(jsonPath("$.category.name", is("Soft Drinks")));
    }
}
