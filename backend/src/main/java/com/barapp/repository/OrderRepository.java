package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
