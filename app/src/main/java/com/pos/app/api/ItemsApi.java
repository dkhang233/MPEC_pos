package com.pos.app.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.app.dto.ItemDto;
import com.pos.app.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemsApi extends BaseApi {
    private final ObjectMapper objectMapper = new ObjectMapper();
    // Lấy danh sách sản phẩm
    public List<Item> getItems() {
        // Gửi request lấy danh sách sản phẩm
        String response = request("/items", "GET", null);
        try {
            // Chuyển chuỗi JSON thành List<ItemDto>
            List<ItemDto> res =  objectMapper.readValue(response,objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDto.class));
            List<Item> data =  res.stream().map(ItemDto::mapToItem).toList();
            return  data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Lấy danh sách nhóm sản phẩm
    public static String getItemsKits() {
        return "Danh sách nhóm sản phẩm";
    }
}
