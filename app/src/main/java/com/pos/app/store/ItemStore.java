package com.pos.app.store;

import com.pos.app.model.Inventory;
import com.pos.app.model.Item;
import com.pos.app.model.Location;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.Data;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class ItemStore {
    // Danh sách các item theo vị trí
    public static final MapProperty<String, List<Item>> itemsPerLocation = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));

    
    // Danh sách các item hiển thị trên bảng
    public static final ListProperty<Item> visibleItems = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    // Danh sách các vị trí
    public static final ListProperty<Location> locations = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    // Vị trí hiện tại
    public static Location currentLocation;

    // Danh sách inventory
    public static final MapProperty<Integer, List<Inventory>> inventories = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));   // Lưu inventory cho mỗi item

    // Số lượng item trên mỗi trang
    public static final IntegerProperty pageSize = new SimpleIntegerProperty(20);

    // Số lượng trang
    public static final IntegerProperty pageCount = new SimpleIntegerProperty(0);





    // ---------------------- Khởi tạo dữ liệu mẫu ----------------------//
    static {
        Faker faker = new Faker();

        // Thêm dữ liệu vào danh sách tên vị trí
        locations.get().addAll( new ArrayList<>( List.of( new Location(1,"Stock 1",false),
                new Location(2,"Stock 2",false),
                new Location(3,"Stock 3",false)
        )));

        // Thêm dữ liệu vào danh sách item
        for (Location location : locations){
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
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
                item.getQuantityAtCurrentLocation().set(10);
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

        // Thêm dữ liệu vào danh sách tên vị trí
        locations.add(new Location(0,"Stock 4",true));

        // Vị trí hiện tại
        currentLocation = new Location(0,"",false);

        // Nếu giá trị của vị trí hiện tại thay đổi thì cập nhật danh sách item tương ứng để hiển thị trên bảng
        currentLocation.getName().addListener((observable, oldValue, newValue) -> {
            visibleItems.clear();
            if(itemsPerLocation.containsKey(newValue))
                visibleItems.addAll(itemsPerLocation.get(newValue));
        });

        // Chọn vị trí hiện tại là vị trí đầu tiên
        currentLocation.getName().set(locations.get(0).getName().get());

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

        pageCount.set((int)Math.ceil(visibleItems.sizeProperty().doubleValue() / pageSize.getValue()));   // Khởi tao số lượng trang
        // Nếu số lượng item hiển thị thay đổi thì cập nhật lại số lượng trang
        visibleItems.sizeProperty().addListener((observable, oldValue, newValue) -> {
            int count = Math.max(1,(int)Math.ceil(visibleItems.sizeProperty().doubleValue() / pageSize.getValue()));
            pageCount.set(count);
        });

    }
    
}
