package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.CocktailIngredient;

public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {
}
