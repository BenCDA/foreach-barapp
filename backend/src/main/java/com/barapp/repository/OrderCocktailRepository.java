package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.OrderCocktail;

public interface OrderCocktailRepository extends JpaRepository<OrderCocktail, Long> {
}
