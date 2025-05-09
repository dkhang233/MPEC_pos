package com.pos.backend.controller;

import com.pos.backend.dto.ItemQuantityDto;
import com.pos.backend.model.Inventory;
import com.pos.backend.model.Item;
import com.pos.backend.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Item API", description = "API for managing items")
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items")
    @Operation(summary = "Get all items", description = "Retrieve a list of all items in the system")
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @GetMapping("/items/user")
    @Operation(summary = "Get all items for specific user", description = "Retrieve a list of all items in the system")
    public List<Item> getItemsByUsername(@RequestParam String username) {
        return itemService.getItemsByUsername(username);
    }

    @GetMapping("/items/{id}")
    @Operation(summary = "Get item by ID", description = "Retrieve an item by its ID")
    public Item getItemById(@PathVariable int id) {
        return itemService.getItemById(id);
    }

    @PostMapping("/items")
    @Operation(summary = "Create item", description = "Create a new item")
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "Update item by ID", description = "Update an item by its ID")
    public Item updateItem(@PathVariable int id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/items/{id}")
    @Operation(summary = "Delete item by ID", description = "Delete an item by its ID")
    public void deleteItem(@PathVariable int id) {
        itemService.deleteItem(id);
    }

    @PutMapping("/items/quantity")
    @Operation(summary = "Update item quantity by ID")
    public Inventory updateItemQuantity(@RequestBody ItemQuantityDto quantityDto) {
        return itemService.updateItemQuantity(quantityDto);
    }

    @GetMapping("/items/{itemId}/inventory")
    @Operation(summary = "Get inventory for item by ID", description = "Retrieve the inventory for a specific item by its ID")
    public List<Inventory> getInventoryForItem(@PathVariable int itemId) {
        return itemService.getInventoryForItem(itemId);
    }
}
