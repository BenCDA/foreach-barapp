package com.barapp.repository;

import com.barapp.model.Order;
import com.barapp.model.OrderCocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderCocktailRepository extends JpaRepository<OrderCocktail, Long> {
    // Permet de récupérer toutes les lignes (cocktails) d’une commande
    List<OrderCocktail> findByOrder(Order order);
}
