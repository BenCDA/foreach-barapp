package com.barapp.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.barapp.dto.CocktailSizePriceRequest;
import com.barapp.dto.CocktailSizePriceResponse;
import com.barapp.model.Cocktail;
import com.barapp.model.CocktailSizePrice;
import com.barapp.model.Size;
import com.barapp.repository.CocktailRepository;
import com.barapp.repository.CocktailSizePriceRepository;
import com.barapp.repository.SizeRepository;
import com.barapp.service.impl.CocktailSizePriceServiceImpl;

public class CocktailSizePriceServiceTest {

    private CocktailSizePriceRepository repository;
    private CocktailRepository cocktailRepository;
    private SizeRepository sizeRepository;
    private CocktailSizePriceService service;

    @BeforeEach
    void setUp() {
        repository = mock(CocktailSizePriceRepository.class);
        cocktailRepository = mock(CocktailRepository.class);
        sizeRepository = mock(SizeRepository.class);
        service = new CocktailSizePriceServiceImpl(repository, cocktailRepository, sizeRepository);
    }

    @Test
    void create_shouldReturnCocktailSizePriceResponse_whenValidRequest() {
        CocktailSizePriceRequest request = new CocktailSizePriceRequest();
        request.setCocktailId(1L);
        request.setSizeId(2L);
        request.setPrice(950);

        Cocktail cocktail = new Cocktail(); cocktail.setId(1L);
        Size size = new Size(); size.setId(2L);

        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));
        when(sizeRepository.findById(2L)).thenReturn(Optional.of(size));

        CocktailSizePrice saved = new CocktailSizePrice(10L, cocktail, size, 950);
        when(repository.save(any())).thenReturn(saved);

        CocktailSizePriceResponse response = service.create(request);

        assertNotNull(response);
        assertEquals(950, response.getPrice());
        assertEquals(1L, response.getCocktailId());
        assertEquals(2L, response.getSizeId());
    }
}
