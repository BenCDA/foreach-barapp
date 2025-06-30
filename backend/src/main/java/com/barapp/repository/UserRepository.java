package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
