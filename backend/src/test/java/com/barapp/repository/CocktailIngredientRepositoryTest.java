package com.barapp.repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.barapp.model.CocktailIngredient;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)  // Ne pas remplacer la datasource configurée
public class CocktailIngredientRepositoryTest {

    @Autowired
    private CocktailIngredientRepository cocktailIngredientRepository;

    @Test
    public void shouldSaveAndFindById() {
        CocktailIngredient ci = new CocktailIngredient();
        // Configure ton objet CocktailIngredient ici avec des valeurs valides
        // Par exemple ci.setCocktail(...); ci.setIngredient(...); ci.setQuantity("10ml");

        CocktailIngredient saved = cocktailIngredientRepository.save(ci);

        Optional<CocktailIngredient> found = cocktailIngredientRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    public void shouldReturnEmptyWhenNotFound() {
        Optional<CocktailIngredient> found = cocktailIngredientRepository.findById(-1L);
        assertThat(found).isNotPresent();
    }
}
