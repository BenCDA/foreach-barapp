package com.barapp.service;

import java.util.List;

import com.barapp.dto.CategoryRequest;
import com.barapp.dto.CategoryResponse;

public interface CategoryService {
    CategoryResponse create(CategoryRequest request);
    List<CategoryResponse> getAll();
    CategoryResponse getById(Long id);
    CategoryResponse update(Long id, CategoryRequest request);
    void delete(Long id);
}
