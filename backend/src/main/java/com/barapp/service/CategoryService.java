package com.barapp.service;

import java.util.List;

import com.barapp.dto.CategoryRequest;
import com.barapp.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    List<CategoryResponse> getAll();
    CategoryResponse getById(Long id);
    void delete(Long id);
}
