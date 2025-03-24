package com.pos.backend.controller;

import com.pos.backend.model.Item;
import com.pos.backend.service.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public Item updaItem(@PathVariable int id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }
}
