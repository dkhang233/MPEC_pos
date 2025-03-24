package com.pos.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.backend.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
