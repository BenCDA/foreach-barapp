package com.barapp.service.impl;

import com.barapp.dto.CategoryRequest;
import com.barapp.dto.CategoryResponse;
import com.barapp.model.Category;
import com.barapp.repository.CategoryRepository;
import com.barapp.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse create(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());

        Category saved = categoryRepository.save(category);
        return new CategoryResponse(saved.getId(), saved.getName());
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category cat = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return new CategoryResponse(cat.getId(), cat.getName());
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
