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
import java.time.LocalDateTime;
import java.util.*;

@Data
public class ItemStore {
    // Danh sách các item
    public static final Map<String, List<Item>> itemsPerLocation = new HashMap<>();
    
    // Danh sách các item hiển thị trên bảng
    public static final ObservableList<Item> visibleItems = FXCollections.observableArrayList();

    public static final List<Location> locations = new ArrayList<>();

    public static Location currentLocation;

    public static final HashMap<Integer, List<Inventory>> inventories = new HashMap<>();   // Lưu inventory cho mỗi item





    // ---------------------- Khởi tạo dữ liệu mẫu ----------------------//
    static {
        Faker faker = new Faker();

        // Thêm dữ liệu vào danh sách tên vị trí
        locations.addAll( new ArrayList<>( List.of( new Location(1,"Stock 1",false),
                new Location(2,"Stock 2",false)
        )));

        // Chọn vị trí hiện tại
        currentLocation = new Location(0,"",false);
        currentLocation.getName().addListener((observable, oldValue, newValue) -> {
            visibleItems.forEach(item -> {
               item.setQuantityAtCurrentLocation(item.getQuantityPerLocation().stream().filter(itemQuantity -> itemQuantity.getLocationName().getValue().equals(newValue)).findFirst().orElse(new ItemQuantity(0,"",0)).getQuantity().getValue());
            });
        });
        currentLocation.getName().set(locations.get(0).getName().get());

        // Thêm dữ liệu vào danh sách item
        for (Location location : locations){
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Item item = new Item();
                item.getId().set(i + 1);
                item.getItemName().set(faker.commerce().productName());
                item.getBarcode().set(faker.number().digits(12));
                item.getCategory().set(faker.lorem().word());
                item.getStockType().set("Stock");
                item.getItemType().set("Standard");
                item.getSupplier().set(faker.lorem().word());
                item.getWholesalePrice().set(2500);
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
                item.getAvatar().set("");
                item.getAllowAlternateDescription().set(false);
                item.getHasSerialNumber().set(false);
                item.getDeleted().set(false);
                items.add(item);
            }
            itemsPerLocation.put(location.getName().get(), items);
        }

        visibleItems.addAll(itemsPerLocation.get(currentLocation.getName().get()));

        // Thêm dữ liệu vào bảng inventory
        inventories.put(1,new ArrayList<>());
        inventories.put(2,new ArrayList<>());
        inventories.put(3,new ArrayList<>());
        for (int i = 0 ; i < 5; i++){
            Inventory inventory = new Inventory();
            inventory.getId().set(faker.number().randomDigit());
            inventory.getItem().set(faker.number().randomDigit());
            inventory.getUsername().set(faker.name().firstName());
            inventory.getTimestamp().set(LocalDateTime.now());
            inventory.getComment().set(faker.lorem().sentence());
            inventory.getLocation().set(locations.get(0).getName().get());
            inventory.getInventory().set(faker.number().numberBetween(-100,100));
            inventory.getAfterInventory().set(faker.number().numberBetween(100,500));
            ItemStore.inventories.get(1).add(inventory);
            ItemStore.inventories.get(2).add(inventory);
            ItemStore.inventories.get(3).add(inventory);
        }
        for (int i = 0 ; i < 5; i++){
            Inventory inventory = new Inventory();
            inventory.getId().set(faker.number().randomDigit());
            inventory.getItem().set(faker.number().randomDigit());
            inventory.getUsername().set(faker.name().firstName());
            inventory.getTimestamp().set(LocalDateTime.now());
            inventory.getComment().set(faker.lorem().sentence());
            inventory.getLocation().set(locations.get(1).getName().get());
            inventory.getInventory().set(faker.number().numberBetween(-100,100));
            inventory.getAfterInventory().set(faker.number().numberBetween(100,500));
            ItemStore.inventories.get(1).add(inventory);
            ItemStore.inventories.get(2).add(inventory);
            ItemStore.inventories.get(3).add(inventory);
        }

    }
    
}
