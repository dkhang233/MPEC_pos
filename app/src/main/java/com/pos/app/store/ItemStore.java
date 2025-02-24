package com.pos.app.store;

import com.pos.app.model.Inventory;
import com.pos.app.model.Item;
import com.pos.app.model.ItemQuantity;
import com.pos.app.model.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ItemStore {
    // Danh sách các item
    public static final List<Item> items = new ArrayList<>();

    // Danh sách các item hiển thị trên bảng
    public static final ObservableList<Item> visibleItems = FXCollections.observableArrayList(items);

    public static final List<String> locationNames = new ArrayList<>();

    public static String currentLocation;

    public static final List<Inventory> inventoryList = new ArrayList<>();

    static {
        // Thêm dữ liệu vào bảng
        items.addAll(Arrays.asList(
                // Mẫu 1: Điện thoại thông minh
                Item.builder()
                        .id(2)
                        .itemName("Smartphone ABC")
                        .barcode("9876543210987")
                        .category("Electronics")
                        .supplier("XYZ Supplier")
                        .wholesalePrice(300.00)
                        .retailPrice(450.00)
                        .tax1Name("VAT")
                        .tax1(8.0)
                        .tax2Name("Luxury Tax")
                        .tax2(2.0)
                        .hsnCode("HSN5678")
                        .quantityPerLocation(List.of(
                                        ItemQuantity.builder()
                                                .locationName("Stock 1")
                                                .quantity(20)
                                                .build(),
                                        ItemQuantity.builder()
                                                .locationName("Stock 2")
                                                .quantity(10)
                                                .build()
                        ))
                        .receivingQuantity(20)
                        .reorderLevel(10)
                        .description("Latest model smartphone with advanced features")
                        .avatar("smartphone_abc.png")
                        .allowAlternateDescription(false)
                        .hasSerialNumber(true)
                        .deleted(false)
                        .build(),

// Mẫu 2: Máy giặt
                Item.builder()
                        .id(3)
                        .itemName("Washing Machine 123")
                        .barcode("1928374650912")
                        .category("Home Appliances")
                        .supplier("HomeTech Supplier")
                        .wholesalePrice(200.00)
                        .retailPrice(350.00)
                        .tax1Name("VAT")
                        .tax1(12.0)
                        .tax2Name("Eco Tax")
                        .tax2(3.0)
                        .hsnCode("HSN9101")
                        .quantityPerLocation(List.of(
                                ItemQuantity.builder()
                                        .locationName("Stock 1")
                                        .quantity(10)
                                        .build(),
                                ItemQuantity.builder()
                                        .locationName("Stock 2")
                                        .quantity(10)
                                        .build()
                        ))
                        .receivingQuantity(5)
                        .reorderLevel(3)
                        .description("Energy-efficient washing machine with multiple modes")
                        .avatar("washing_machine_123.png")
                        .allowAlternateDescription(true)
                        .hasSerialNumber(true)
                        .deleted(false)
                        .build(),

// Mẫu 3: Sách
                Item.builder()
                        .id(4)
                        .itemName("Programming in Java")
                        .barcode("5647382910123")
                        .category("Books")
                        .supplier("BookWorld")
                        .wholesalePrice(20.00)
                        .retailPrice(30.00)
                        .tax1Name("GST")
                        .tax1(5.0)
                        .tax2Name("Education Cess")
                        .tax2(1.0)
                        .hsnCode("HSN1122")
                        .quantityPerLocation(List.of(
                                ItemQuantity.builder()
                                        .locationName("Stock 1")
                                        .quantity(10)
                                        .build(),
                                ItemQuantity.builder()
                                        .locationName("Stock 2")
                                        .quantity(30)
                                        .build()
                        ))
                        .receivingQuantity(50)
                        .reorderLevel(20)
                        .description("Comprehensive guide to Java programming")
                        .avatar("programming_in_java.png")
                        .allowAlternateDescription(false)
                        .hasSerialNumber(false)
                        .deleted(false)
                        .build(),

// Mẫu 4: Bàn làm việc
                Item.builder()
                        .id(5)
                        .itemName("Ergonomic Office Desk")
                        .barcode("3216549870123")
                        .category("Furniture")
                        .supplier("FurniCo")
                        .wholesalePrice(150.00)
                        .retailPrice(250.00)
                        .tax1Name("VAT")
                        .tax1(10.0)
                        .tax2Name("Luxury Tax")
                        .tax2(2.0)
                        .hsnCode("HSN3344")
                        .quantityPerLocation(List.of(
                                ItemQuantity.builder()
                                        .locationName("Stock 1")
                                        .quantity(10)
                                        .build(),
                                ItemQuantity.builder()
                                        .locationName("Stock 2")
                                        .quantity(8)
                                        .build()
                        ))
                        .receivingQuantity(5)
                        .reorderLevel(2)
                        .description("Height-adjustable ergonomic office desk")
                        .avatar("ergonomic_office_desk.png")
                        .allowAlternateDescription(true)
                        .hasSerialNumber(false)
                        .deleted(false)
                        .build(),

// Mẫu 5: Tai nghe
                Item.builder()
                        .id(6)
                        .itemName("Wireless Headphones")
                        .barcode("4567891234567")
                        .category("Accessories")
                        .supplier("SoundTech")
                        .wholesalePrice(50.00)
                        .retailPrice(80.00)
                        .tax1Name("VAT")
                        .tax1(8.0)
                        .tax2Name("Import Duty")
                        .tax2(5.0)
                        .hsnCode("HSN7788")
                        .quantityPerLocation(List.of(
                                ItemQuantity.builder()
                                        .locationName("Stock 1")
                                        .quantity(10)
                                        .build(),
                                ItemQuantity.builder()
                                        .locationName("Stock 2")
                                        .quantity(15)
                                        .build()
                        ))
                        .receivingQuantity(30)
                        .reorderLevel(15)
                        .description("Noise-cancelling wireless headphones with long battery life")
                        .avatar("wireless_headphones.png")
                        .allowAlternateDescription(false)
                        .hasSerialNumber(true)
                        .deleted(false)
                        .build()
        ));

        // Thêm dữ liệu vào danh sách hiển thị
        visibleItems.addAll(items);


        // Thêm dữ liệu vào danh sách tên vị trí
        locationNames.addAll(List.of("Stock 1","Stock 2"));

        // Chọn vị trí hiện tại
        currentLocation = locationNames.get(1);
    }
}
