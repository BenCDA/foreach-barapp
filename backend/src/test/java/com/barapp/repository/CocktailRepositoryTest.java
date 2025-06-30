package com.barapp.repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.barapp.model.Cocktail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // <-- ici !
public class CocktailRepositoryTest {

    @Autowired
    private CocktailRepository cocktailRepository;

    @Test
    public void shouldSaveAndFindById() {
        Cocktail cocktail = new Cocktail();
        cocktail.setName("Mojito");
        cocktail.setDescription("Minty fresh");
        cocktail.setImageUrl("mojito.png");
        // tu peux aussi setCategory ici si besoin

        Cocktail saved = cocktailRepository.save(cocktail);

        Optional<Cocktail> found = cocktailRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Mojito");
    }

    @Test
    public void shouldReturnEmptyWhenNotFound() {
        Optional<Cocktail> found = cocktailRepository.findById(-1L);
        assertThat(found).isNotPresent();
    }
}
