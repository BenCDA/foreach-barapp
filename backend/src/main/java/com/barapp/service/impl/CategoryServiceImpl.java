package com.barapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.barapp.dto.CategoryRequest;
import com.barapp.dto.CategoryResponse;
import com.barapp.model.Category;
import com.barapp.repository.CategoryRepository;
import com.barapp.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    @Override
    public CategoryResponse create(CategoryRequest req) {
        Category c = new Category();
        c.setName(req.getName());
        Category saved = repo.save(c);
        return new CategoryResponse(saved.getId(), saved.getName());
    }

    @Override
    public List<CategoryResponse> getAll() {
        return repo.findAll().stream()
                   .map(c -> new CategoryResponse(c.getId(), c.getName()))
                   .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category c = repo.findById(id)
                         .orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable"));
        return new CategoryResponse(c.getId(), c.getName());
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest req) {
        Category c = repo.findById(id)
                         .orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable"));
        c.setName(req.getName());
        Category updated = repo.save(c);
        return new CategoryResponse(updated.getId(), updated.getName());
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
