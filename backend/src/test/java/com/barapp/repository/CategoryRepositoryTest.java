package com.barapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.barapp.model.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // <== ajout
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should save category and find it by name")
    void shouldSaveAndFindByName() {
        Category category = new Category();
        category.setName("Mocktails");

        categoryRepository.save(category);

        Category found = categoryRepository.findById(category.getId()).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Mocktails");
    }

    @Test
    @DisplayName("Should return empty when category not found")
    void shouldReturnEmptyWhenNotFound() {
        var result = categoryRepository.findById(999L);
        assertThat(result).isEmpty();
    }
}
