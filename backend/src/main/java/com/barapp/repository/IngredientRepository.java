package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
