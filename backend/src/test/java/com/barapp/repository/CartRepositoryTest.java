package com.barapp.repository;

import com.barapp.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @Test
    void shouldSaveAndFindByUser() {
        User user = new User();
        user.setName("Ben");
        user.setEmail("ben@test.com");
        user.setPassword("pwd");
        userRepository.save(user);

        Cocktail cocktail = new Cocktail();
        cocktail.setName("Mojito");
        cocktailRepository.save(cocktail);

        Size size = new Size();
        size.setLabel("M");
        sizeRepository.save(size);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCocktail(cocktail);
        cart.setSize(size);
        cartRepository.save(cart);

        List<Cart> list = cartRepository.findByUser(user);
        assertThat(list).hasSize(1);
    }
}
