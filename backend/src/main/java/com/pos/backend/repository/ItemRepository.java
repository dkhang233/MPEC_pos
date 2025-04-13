package com.pos.backend.repository;

import com.pos.backend.model.Item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    public List<Item> findByOwnedBy(String username);
}
