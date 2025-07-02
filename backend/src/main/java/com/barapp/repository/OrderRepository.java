// OrderRepository.java
package com.barapp.repository;

import com.barapp.model.Order;
import com.barapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // pour getByUserEmail(...)
    List<Order> findByUser(User user);

    // pour getByStatusNotFinished()
    // récupère toutes les commandes dont le statut n'est pas TERMINEE
    List<Order> findByStatusNot(Order.Status status);
}
