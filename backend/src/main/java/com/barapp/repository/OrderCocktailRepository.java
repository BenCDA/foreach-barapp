// src/main/java/com/barapp/repository/OrderCocktailRepository.java
package com.barapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.OrderCocktail;

public interface OrderCocktailRepository extends JpaRepository<OrderCocktail, Long> {
    // cherche tous les “orderCocktail” d’une commande donnée
    List<OrderCocktail> findByOrderId(Long orderId);
}
