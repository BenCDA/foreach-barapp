package com.barapp.repository;

import com.barapp.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderCocktailRepositoryTest {

    @Autowired
    private OrderCocktailRepository repository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindByOrderId() {
        User user = new User();
        user.setName("Ben");
        user.setEmail("ben@test.com");
        user.setPassword("pwd");
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.COMMANDEE);
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);

        Cocktail c = new Cocktail();
        c.setName("Mojito");
        cocktailRepository.save(c);

        Size s = new Size();
        s.setLabel("M");
        sizeRepository.save(s);

        OrderCocktail oc = new OrderCocktail();
        oc.setOrder(order);
        oc.setCocktail(c);
        oc.setSize(s);
        oc.setStep(OrderCocktail.Step.PREPARATION);
        repository.save(oc);

        List<OrderCocktail> list = repository.findByOrderId(order.getId());
        assertThat(list).hasSize(1);
    }
}
