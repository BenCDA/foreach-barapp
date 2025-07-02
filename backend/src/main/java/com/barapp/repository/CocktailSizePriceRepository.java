package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.CocktailSizePrice;
import java.util.List;

public interface CocktailSizePriceRepository extends JpaRepository<CocktailSizePrice, Long> {
    // --- NOUVELLE METHODE ---
    List<CocktailSizePrice> findByCocktailId(Long cocktailId);
}
