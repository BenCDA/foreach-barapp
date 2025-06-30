package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
