package com.pos.backend.service;

import com.pos.backend.model.Item;
import com.pos.backend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> getItems() {
        List<Item> result = itemRepository.findAll();
        return result;
    }

    public Item getItemById(int id) {
        Item result = itemRepository.findById(id).orElse(null);
        return result;
    }

    public Item createItem(Item item) {
        Item result = itemRepository.save(item);
        return result;
    }

    public Item updateItem(int id, Item item) {
        Item result = itemRepository.findById(id).orElse(null);
        if (result != null) {
            result.setName(item.getName());
            result.setCategory(item.getCategory());
            result.setSupplier(item.getSupplier());
            result.setBarcode(item.getBarcode());
            result.setDescription(item.getDescription());
            result.setCostPrice(item.getCostPrice());
            result.setSellingPrice(item.getSellingPrice());
            result.setReorderLevel(item.getReorderLevel());
            result.setPicFilename(item.getPicFilename());
            result.setDeleted(item.getDeleted());
            itemRepository.save(result);
        }
        return result;
    }
}
