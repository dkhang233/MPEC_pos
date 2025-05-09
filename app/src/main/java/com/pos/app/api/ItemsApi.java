package com.pos.app.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pos.app.dto.InventoryDto;
import com.pos.app.dto.ItemDto;
import com.pos.app.dto.ItemQuantityDto;
import com.pos.app.model.Inventory;
import com.pos.app.model.Item;
import com.pos.app.store.UserStore;
import com.pos.app.util.AlertBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemsApi extends BaseApi {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ItemsApi() {
        // Đăng ký module JavaTimeModule để xử lý LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
    }

    // Lấy danh sách sản phẩm cho người dùng hiện tại
    public List<Item> getItems() {
        // Gửi request lấy danh sách sản phẩm
        String endpoint = "/items/user" + "?username=" + UserStore.username;
        String response = request(endpoint, "GET", null);
        try {
            // Chuyển chuỗi JSON thành List<ItemDto>
            List<ItemDto> res = objectMapper.readValue(response,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, ItemDto.class));
            List<Item> data = res.stream().map(ItemDto::mapToItem).toList();
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Tạo mới một sản phẩm
    public Optional<Item> createNewItem(Item item) {
        ItemDto itemDto = new ItemDto(item);
        try {
            String response = request("/items", "POST", itemDto);
            // Chuyển chuỗi JSON thành ItemDto
            ItemDto res = objectMapper.readValue(response, ItemDto.class);
            return Optional.of(res.mapToItem());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Cập nhật thông tin sản phẩm
    public Optional<Item> updateItem(Item item) {
        ItemDto itemDto = new ItemDto(item);
        try {
            String response = request("/items/" + item.getId().get(), "PUT", itemDto);
            // Chuyển chuỗi JSON thành ItemDto
            ItemDto res = objectMapper.readValue(response, ItemDto.class);
            return Optional.of(res.mapToItem());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Cập nhập số lượng sản phẩm
    public Optional<Inventory> updateItemQuantity(ItemQuantityDto itemQuantityDto) {
        try {
            InventoryDto res = objectMapper.readValue(request("/items/quantity", "PUT", itemQuantityDto),
                    InventoryDto.class);
            return Optional.of(res.mapToInventory());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Lấy lịch sử thay đổi số lượng sản phẩm
    public List<Inventory> getItemQuantityHistory(int itemId) {
        String endpoint = "/items/" + itemId + "/inventory";
        String response = request(endpoint, "GET", null);
        try {
            // Chuyển chuỗi JSON thành List<ItemQuantityDto>
            List<InventoryDto> res = objectMapper.readValue(response,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, InventoryDto.class));
            List<Inventory> data = res.stream().map(InventoryDto::mapToInventory).toList();
            return data;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void deleteItem(Integer value) {
        String endpoint = "/items/" + value;
        request(endpoint, "DELETE", null);
    }

}
