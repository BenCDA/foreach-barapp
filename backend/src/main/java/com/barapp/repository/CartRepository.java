package com.barapp.repository;

import com.barapp.model.Cart;
import com.barapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    void deleteByUser(User user);
}
