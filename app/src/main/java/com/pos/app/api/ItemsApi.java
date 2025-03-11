package com.pos.app.api;

import java.io.IOException;

public class ItemsApi {
    // Lấy danh sách sản phẩm
    public static String getItems() {
        return "Danh sách sản phẩm";
    }

    // Lấy danh sách nhóm sản phẩm
    public static String getItemsKits() {
        return "Danh sách nhóm sản phẩm";
    }

    public static void deleteItem(int itemId){
        try {
            BaseApi.request("item","DELETE",itemId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
