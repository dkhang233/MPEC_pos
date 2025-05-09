package com.pos.backend.service;

import com.pos.backend.dto.ItemQuantityDto;
import com.pos.backend.model.Inventory;
import com.pos.backend.model.Item;
import com.pos.backend.repository.InventoryRepository;
import com.pos.backend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;

    public List<Item> getItems() {
        List<Item> result = itemRepository.findAll();
        return result;
    }

    public List<Item> getItemsByUsername(String username) {
        List<Item> result = itemRepository.findByOwnedBy(username);
        return result;
    }

    public Item getItemById(int id) {
        Item result = itemRepository.findById(id).orElse(null);
        return result;
    }

    public Item createItem(Item item) {
        Item newItem = new Item();
        newItem.setName(item.getName());
        newItem.setCategory(item.getCategory());
        newItem.setSupplier(item.getSupplier());
        newItem.setBarcode(item.getBarcode());
        newItem.setDescription(item.getDescription());
        newItem.setCostPrice(item.getCostPrice());
        newItem.setSellingPrice(item.getSellingPrice());
        newItem.setQuantity(item.getQuantity());
        newItem.setReorderLevel(item.getReorderLevel());
        newItem.setPicFilename(item.getPicFilename());
        newItem.setDeleted(item.getDeleted());
        newItem.setOwnedBy(item.getOwnedBy());

        Item result = itemRepository.save(newItem);
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
            result.setQuantity(item.getQuantity());
            result.setReorderLevel(item.getReorderLevel());
            result.setPicFilename(item.getPicFilename());
            result.setDeleted(item.getDeleted());
            itemRepository.save(result);
        }
        return result;
    }

    public Inventory updateItemQuantity(ItemQuantityDto quantityDto) {
        Item result = itemRepository.findById(quantityDto.getItemId()).orElse(null);
        if (result != null) {
            result.setQuantity(quantityDto.getChangedQuantity() + result.getQuantity());
            itemRepository.save(result);

            Inventory inventory = new Inventory();
            inventory.setItem(quantityDto.getItemId());
            inventory.setChangedQuantity(quantityDto.getChangedQuantity());
            inventory.setAfterQuantity(result.getQuantity());
            inventory.setComment(quantityDto.getComment());
            inventory.setTimestamp(LocalDateTime.now());
            return inventoryRepository.save(inventory);
        }
        return null;
    }

    public List<Inventory> getInventoryForItem(Integer itemId) {
        List<Inventory> result = inventoryRepository.findByItem(itemId);
        return result;
    }

    public void deleteItem(int id) {
        Item result = itemRepository.findById(id).orElse(null);
        if (result != null) {
            itemRepository.delete(result);
        }
    }
}
