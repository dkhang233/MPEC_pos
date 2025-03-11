package com.pos.backend.dao;

import com.pos.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDAOInterface extends JpaRepository<Item,Integer> {

}
