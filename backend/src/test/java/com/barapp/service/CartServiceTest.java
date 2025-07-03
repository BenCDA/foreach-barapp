package com.barapp.service;

import com.barapp.dto.CartRequest;
import com.barapp.dto.CartResponse;
import com.barapp.model.*;
import com.barapp.repository.*;
import com.barapp.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.barapp.model.User.Role.CLIENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    private UserRepository userRepository;
    private CocktailRepository cocktailRepository;
    private SizeRepository sizeRepository;
    private CartRepository cartRepository;
    private CocktailSizePriceRepository cocktailSizePriceRepository;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        cocktailRepository = mock(CocktailRepository.class);
        sizeRepository = mock(SizeRepository.class);
        cartRepository = mock(CartRepository.class);
        cocktailSizePriceRepository = mock(CocktailSizePriceRepository.class);

        cartService = new CartServiceImpl(userRepository, cocktailRepository, sizeRepository,
                cartRepository, cocktailSizePriceRepository);
    }

    @Test
    void addToCart_shouldSaveCart() {
        User user = new User(1L, "Ben", "ben@test.com", "pwd", CLIENT);
        when(userRepository.findByEmail("ben@test.com")).thenReturn(Optional.of(user));
        Cocktail cocktail = new Cocktail(1L, "Mojito", "d", "i", null);
        when(cocktailRepository.findById(1L)).thenReturn(Optional.of(cocktail));
        Size size = new Size(1L, "M");
        when(sizeRepository.findById(2L)).thenReturn(Optional.of(size));

        CartRequest req = new CartRequest();
        req.setCocktailId(1L);
        req.setSizeId(2L);

        cartService.addToCart("ben@test.com", req);

        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void getCart_shouldReturnResponses() {
        User user = new User(1L, "Ben", "ben@test.com", "pwd", CLIENT);
        when(userRepository.findByEmail("ben@test.com")).thenReturn(Optional.of(user));
        Cocktail cocktail = new Cocktail(1L, "Mojito", "d", "i", null);
        Size size = new Size(1L, "M");
        Cart cart = new Cart(1L, user, cocktail, size);
        when(cartRepository.findByUser(user)).thenReturn(List.of(cart));
        CocktailSizePrice csp = new CocktailSizePrice(1L, cocktail, size, 5);
        when(cocktailSizePriceRepository.findByCocktailId(1L)).thenReturn(List.of(csp));

        List<CartResponse> responses = cartService.getCart("ben@test.com");

        assertEquals(1, responses.size());
        assertEquals("Mojito", responses.get(0).getCocktailName());
        assertEquals(5, responses.get(0).getPrice());
    }
}
