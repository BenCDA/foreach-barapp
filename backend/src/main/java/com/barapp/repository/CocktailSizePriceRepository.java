package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.CocktailSizePrice;

public interface CocktailSizePriceRepository extends JpaRepository<CocktailSizePrice, Long> {
}
