package com.pos.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pos.backend.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findByItem(int itemId); // Find inventory by item ID
}
