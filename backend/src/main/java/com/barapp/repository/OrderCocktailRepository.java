// OrderCocktailRepository.java
package com.barapp.repository;

import com.barapp.model.OrderCocktail;
import com.barapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderCocktailRepository extends JpaRepository<OrderCocktail, Long> {
    // pour OrderServiceImpl#getByIdAndUserEmail et updateStatus(...)
    List<OrderCocktail> findByOrder(Order order);

    // pour OrderCocktailServiceImpl#getByOrderId(...)
    List<OrderCocktail> findByOrderId(Long orderId);
}
