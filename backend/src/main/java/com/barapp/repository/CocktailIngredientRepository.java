package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.barapp.model.CocktailIngredient;

public interface CocktailIngredientRepository extends JpaRepository<CocktailIngredient, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CocktailIngredient ci WHERE ci.cocktail.id = :cocktailId")
    void deleteAllByCocktailId(Long cocktailId);
}
