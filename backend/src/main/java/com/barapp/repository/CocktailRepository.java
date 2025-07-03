package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.barapp.model.Cocktail;
import com.barapp.model.Category;
import java.util.List;

public interface CocktailRepository extends JpaRepository<Cocktail, Long> {
    /**
     * Recherche par catégorie.
     */
    List<Cocktail> findByCategory(Category category);
}
