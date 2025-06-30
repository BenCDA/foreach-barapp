package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.Cocktail;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
}
