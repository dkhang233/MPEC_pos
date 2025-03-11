package com.pos.backend.service;

import com.pos.backend.dao.ItemDAOInterface;
import com.pos.backend.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService implements ItemServiceInterface{
    private final ItemDAOInterface itemDAOInterface;

    @Autowired
    public ItemService(ItemDAOInterface daoInterface) {itemDAOInterface = daoInterface;}

    @Override
    public List<Item> findAll() {
        return itemDAOInterface.findAll();
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        itemDAOInterface.deleteById(id);
    }

    @Transactional
    @Override
    public Item update(Item item) {
        return itemDAOInterface.save(item);
    }
//
//    @Override
//    public Item find() {
//        return itemDAOInterface.find();
//    }
}
