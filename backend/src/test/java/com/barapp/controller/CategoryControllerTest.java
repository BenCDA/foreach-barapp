package com.barapp.controller;

import com.barapp.dto.CategoryRequest;
import com.barapp.dto.CategoryResponse;
import com.barapp.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(com.barapp.TestSecurityConfig.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    // GET all
    @Test
    void shouldReturnOkWhenGetAllCategories() throws Exception {
        Mockito.when(categoryService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // GET by id
    @Test
    void shouldReturnOkWhenGetCategoryById() throws Exception {
        CategoryResponse response = new CategoryResponse(1L, "Soft");
        Mockito.when(categoryService.getById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Soft")));
    }

    // POST (create)
    @Test
    void shouldCreateCategoryAndReturn201() throws Exception {
        CategoryRequest request = new CategoryRequest("Cocktails");
        CategoryResponse response = new CategoryResponse(2L, "Cocktails");

        Mockito.when(categoryService.create(Mockito.any(CategoryRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Cocktails\"}")) // ✅ corrigé ici
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Cocktails")));
    }

    // POST (invalid request)
    @Test
    void shouldReturnBadRequestWhenPostInvalidCategory() throws Exception {
        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    // DELETE
    @Test
    void shouldDeleteCategoryAndReturn204() throws Exception {
        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());
    }
}
