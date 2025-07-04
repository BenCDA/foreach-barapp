package com.barapp.service.impl;

import com.barapp.dto.CartRequest;
import com.barapp.dto.CartResponse;
import com.barapp.model.*;
import com.barapp.repository.*;
import com.barapp.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CocktailRepository cocktailRepository;
    private final SizeRepository sizeRepository;
    private final CartRepository cartRepository;
    private final CocktailSizePriceRepository cocktailSizePriceRepository;

    @Override
    public void addToCart(String email, CartRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Cocktail cocktail = cocktailRepository.findById(request.getCocktailId()).orElseThrow();
        Size size = sizeRepository.findById(request.getSizeId()).orElseThrow();

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCocktail(cocktail);
        cart.setSize(size);
        cartRepository.save(cart);
    }

    @Override
    public List<CartResponse> getCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        List<Cart> carts = cartRepository.findByUser(user);

        return carts.stream().map(cart -> {
            CartResponse response = new CartResponse();
            response.setId(cart.getId());
            response.setCocktailName(cart.getCocktail().getName());
            response.setSizeLabel(cart.getSize().getLabel());

            // Récupère le prix à partir de CocktailSizePriceRepository
            Integer price = cocktailSizePriceRepository
                .findByCocktailId(cart.getCocktail().getId()).stream()
                .filter(csp -> csp.getSize().getId().equals(cart.getSize().getId()))
                .map(CocktailSizePrice::getPrice)
                .findFirst()
                .orElse(0);

            response.setPrice(price);
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public void clearCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        cartRepository.deleteByUser(user);
    }
}
