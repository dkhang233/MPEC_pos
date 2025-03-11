package com.pos.backend.service;

import com.pos.backend.entity.Item;

import java.util.List;

public interface ItemServiceInterface {
    List<Item> findAll();
    void deleteById(int id);
    Item update(Item item);
//    Item find();
}
