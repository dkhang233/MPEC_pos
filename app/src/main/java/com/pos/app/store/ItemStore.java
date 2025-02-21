package com.pos.app.store;

import com.pos.app.model.Inventory;
import com.pos.app.model.Item;
import com.pos.app.model.ItemQuantity;
import com.pos.app.model.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import net.datafaker.Faker;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Data
public class ItemStore {
    // Danh sách các item
    public static final List<Item> items = new ArrayList<>();

    // Danh sách các item hiển thị trên bảng
    public static final ObservableList<Item> visibleItems = FXCollections.observableArrayList(items);

    public static final List<Location> locations = new ArrayList<>();

    public static Location currentLocation;

    public static final HashMap<Integer, List<Inventory>> inventories = new HashMap<>();   // Lưu inventory cho mỗi item









    // ---------------------- Khởi tạo dữ liệu mẫu ----------------------//
    static {
        Faker faker = new Faker();
        // Thêm dữ liệu vào danh sách item
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.getId().set(faker.number().numberBetween(1, 1000));
            item.getItemName().set(faker.commerce().productName());
            item.getBarcode().set(faker.number().digits(12));
            item.getCategory().set(faker.lorem().word());
//            item.getAttributes().set(attributes.getValue());
            item.getStockType().set("Stock");
            item.getItemType().set("Standard");
            item.getSupplier().set(faker.lorem().word());
            item.getWholesalePrice().set(faker.number().randomDouble(2, 10, 100));
            item.getRetailPrice().set(faker.number().randomDouble(2, 10, 100));
            item.getTax1Name().set(faker.lorem().word());
            item.getTax1().set(faker.number().randomDouble(2, 0, 20));
            item.getTax2Name().set(faker.lorem().word());
            item.getTax2().set(faker.number().randomDouble(2, 0, 20));
            item.getHsnCode().set(faker.number().digits(3));
            List<ItemQuantity> quantityPerLocation = new ArrayList<>();
            quantityPerLocation.add(new ItemQuantity(1, "Stock 1", faker.number().numberBetween(0, 100)));
            quantityPerLocation.add(new ItemQuantity(2, "Stock 2", faker.number().numberBetween(0, 100)));
            item.getQuantityPerLocation().setAll(quantityPerLocation);
            item.getReceivingQuantity().set(10);
            item.getReorderLevel().set(10);
            item.getDescription().set(faker.lorem().sentence());
            item.getAvatar().set(faker.internet().domainName());
            item.getAllowAlternateDescription().set(false);
            item.getHasSerialNumber().set(false);
            item.getDeleted().set(false);
            items.add(item);
        }

        // Thêm dữ liệu vào danh sách hiển thị
        visibleItems.addAll(items);

        // Thêm dữ liệu vào danh sách tên vị trí
        locations.addAll( new ArrayList<>( List.of( new Location(1,"Stock 1",false),
                new Location(1,"Stock 2",false)
        )));

        // Chọn vị trí hiện tại
        currentLocation = locations.get(0);

        // Thêm dữ liệu vào bảng inventory
        inventories.put(2,new ArrayList<>());
        inventories.put(3,new ArrayList<>());
        for (int i = 0 ; i < 20; i++){
            inventories.get(2).add(Inventory.builder()
                    .location("Stock 1")
                    .user(1)
                    .inventory(5)
                    .afterInventory(5)
                    .timestamp(LocalDate.now())
                    .comment("Initial stock")
                    .build());
            inventories.get(2).add(Inventory.builder()
                    .location("Stock 2")
                    .user(2)
                    .inventory(10)
                    .afterInventory(10)
                    .timestamp(LocalDate.now())
                    .comment("Initial stock")
                    .build());
            inventories.get(3).add(Inventory.builder()
                    .location("Stock 1")
                    .user(1)
                    .inventory(5)
                    .afterInventory(5)
                    .timestamp(LocalDate.now())
                    .comment("Initial stock")
                    .build());
            inventories.get(3).add(Inventory.builder()
                    .location("Stock 2")
                    .user(2)
                    .inventory(5)
                    .afterInventory(5)
                    .timestamp(LocalDate.now())
                    .comment("Initial stock")
                    .build());
        }
    }
    
}
