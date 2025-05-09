package com.pos.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.backend.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
