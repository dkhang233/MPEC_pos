package com.pos.backend.rest;

import com.fasterxml.jackson.databind.type.IterationType;
import com.pos.backend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController (ItemService is) {itemService = is;}

    @DeleteMapping("item")
    public String deleteItem(@RequestParam int itemId){
        itemService.deleteById(itemId);
        return "Delete";
    }
}
