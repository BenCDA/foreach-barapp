package com.barapp.repository;

import com.barapp.model.Order;
import com.barapp.model.User;
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
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindByUser() {
        User user = new User();
        user.setName("Ben");
        user.setEmail("ben@test.com");
        user.setPassword("pwd");
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.COMMANDEE);
        order.setDate(LocalDateTime.now());
        orderRepository.save(order);

        List<Order> list = orderRepository.findByUser(user);
        assertThat(list).hasSize(1);
    }

    @Test
    void shouldFindByStatusNot() {
        User user = new User();
        user.setName("Ben");
        user.setEmail("ben2@test.com");
        user.setPassword("pwd");
        userRepository.save(user);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.COMMANDEE);
        order.setDate(LocalDateTime.now());
        orderRepository.save(order);

        List<Order> list = orderRepository.findByStatusNot(Order.Status.TERMINEE);
        assertThat(list).isNotEmpty();
    }
}
