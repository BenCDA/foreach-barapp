package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.barapp.model.CocktailSizePrice;
import java.util.List;
import java.util.Optional;

public interface CocktailSizePriceRepository extends JpaRepository<CocktailSizePrice, Long> {
    List<CocktailSizePrice> findByCocktailId(Long cocktailId);

    // Méthode pour récupérer le prix par cocktail ET taille
    Optional<CocktailSizePrice> findByCocktailIdAndSizeId(Long cocktailId, Long sizeId);

    // Méthode de suppression personnalisée
    @Modifying
    @Transactional
    @Query("DELETE FROM CocktailSizePrice csp WHERE csp.cocktail.id = :cocktailId")
    void deleteAllByCocktailId(Long cocktailId);
}
