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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.barapp.dto.CategoryRequest;
import com.barapp.dto.CategoryResponse;
import com.barapp.model.Category;
import com.barapp.repository.CategoryRepository;
import com.barapp.service.impl.CategoryServiceImpl;

public class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void create_shouldReturnResponse_whenValidRequest() {
        CategoryRequest request = new CategoryRequest();
        request.setName("Classique");

        Category saved = new Category(1L, "Classique");
        when(categoryRepository.save(any(Category.class))).thenReturn(saved);

        CategoryResponse response = categoryService.create(request);

        assertNotNull(response);
        assertEquals("Classique", response.getName());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoneExist() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<CategoryResponse> list = categoryService.getAll();

        assertTrue(list.isEmpty());
    }
}
