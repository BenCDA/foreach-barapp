package com.barapp.repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.barapp.model.Ingredient;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void shouldSaveAndFindById() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Lemon");
        Ingredient saved = ingredientRepository.save(ingredient);

        Optional<Ingredient> found = ingredientRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Lemon");
    }

    @Test
    public void shouldReturnEmptyWhenNotFound() {
        Optional<Ingredient> found = ingredientRepository.findById(-1L);
        assertThat(found).isEmpty();
    }
}
