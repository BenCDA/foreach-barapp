package com.barapp.repository;

import com.barapp.model.Cart;
import com.barapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    void deleteByUser(User user);

    // Ajout : suppression des entrées panier associées à un cocktail
    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.cocktail.id = :cocktailId")
    void deleteAllByCocktailId(Long cocktailId);
}
