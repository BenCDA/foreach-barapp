package com.barapp.repository;

import com.barapp.model.Order;
import com.barapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Permet de récupérer toutes les commandes d’un user
    List<Order> findByUser(User user);
}
