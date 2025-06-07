package com.pos.app.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pos.app.dto.ItemDto;
import com.pos.app.model.Item;
import com.pos.app.model.Supplier;
import com.pos.app.store.UserStore;

import java.util.ArrayList;
import java.util.List;

public class SupplierApi extends BaseApi {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // Lấy danh sách sản phẩm cho người dùng hiện tại
    public List<Supplier> getSuppliers() {
        // Gửi request lấy danh sách sản phẩm
        String endpoint = "/suppliers/user" + "?username=" + UserStore.username;
        String response = request(endpoint, "GET", null);
        try {
            // Chuyển chuỗi JSON thành List<ItemDto>
            List<Supplier> res = objectMapper.readValue(response,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Supplier.class));
            return res;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Supplier getSupplier(String companyName) {
        String endpoint = "/suppliers/" + companyName;
        String response = request(endpoint, "GET", null);
        try {
            Supplier supplier = objectMapper.readValue(response, Supplier.class);
            return supplier;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new Supplier();
        }
    }
}
