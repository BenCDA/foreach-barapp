package com.barapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barapp.model.Size;

public interface SizeRepository extends JpaRepository<Size, Long> {
}
