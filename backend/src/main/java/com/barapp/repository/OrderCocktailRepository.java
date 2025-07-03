package com.barapp.repository;

import com.barapp.model.OrderCocktail;
import com.barapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface OrderCocktailRepository extends JpaRepository<OrderCocktail, Long> {
    List<OrderCocktail> findByOrder(Order order);
    List<OrderCocktail> findByOrderId(Long orderId);

    // Ajout : suppression des cocktails liés à ce cocktail
    @Modifying
    @Transactional
    @Query("DELETE FROM OrderCocktail oc WHERE oc.cocktail.id = :cocktailId")
    void deleteAllByCocktailId(Long cocktailId);
}
