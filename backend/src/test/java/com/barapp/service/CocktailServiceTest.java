package com.barapp.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.barapp.dto.CocktailRequest;
import com.barapp.dto.CocktailResponse;
import com.barapp.model.Category;
import com.barapp.model.Cocktail;
import com.barapp.repository.CategoryRepository;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.CocktailSizePriceRepository;
import com.barapp.repository.CocktailIngredientRepository;
import com.barapp.repository.OrderCocktailRepository;
import com.barapp.repository.CartRepository;
import com.barapp.service.impl.CocktailServiceImpl;

public class CocktailServiceTest {

    private CocktailRepository cocktailRepository;
    private CategoryRepository categoryRepository;
    private CocktailSizePriceRepository cocktailSizePriceRepository;
    private CocktailIngredientRepository cocktailIngredientRepository;
    private OrderCocktailRepository orderCocktailRepository;
    private CartRepository cartRepository;

    private CocktailService cocktailService;

    @BeforeEach
    void setUp() {
        cocktailRepository = mock(CocktailRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        cocktailSizePriceRepository = mock(CocktailSizePriceRepository.class);
        cocktailIngredientRepository = mock(CocktailIngredientRepository.class);
        orderCocktailRepository = mock(OrderCocktailRepository.class);
        cartRepository = mock(CartRepository.class);

        cocktailService = new CocktailServiceImpl(
            cocktailRepository,
            categoryRepository,
            cocktailSizePriceRepository,
            cocktailIngredientRepository,
            orderCocktailRepository,
            cartRepository
        );
    }

    @Test
    void create_shouldReturnCocktailResponse_whenValidRequest() {
        CocktailRequest request = new CocktailRequest();
        request.setName("Mojito");
        request.setDescription("Classic Cuban cocktail");
        request.setImageUrl("url.png");
        request.setCategoryId(1L);

        Category category = new Category(1L, "Classique");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(cocktailRepository.save(any(Cocktail.class)))
                .thenReturn(new Cocktail(1L, "Mojito", "Classic Cuban cocktail", "url.png", category));

        CocktailResponse response = cocktailService.create(request);

        assertNotNull(response);
        assertEquals("Mojito", response.getName());
        assertEquals("Classique", response.getCategory().getName());
    }
}
