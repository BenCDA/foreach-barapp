// src/main/java/com/barapp/repository/OrderRepository.java
package com.barapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // renvoie toutes les commandes d’un utilisateur
    List<Order> findByUserEmail(String email);
    // renvoie toutes les commandes dont le statut n’est pas terminé
    List<Order> findByStatusNot(Order.Status status);
}
