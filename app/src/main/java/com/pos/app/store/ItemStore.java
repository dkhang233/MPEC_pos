package com.pos.app.store;

import com.pos.app.model.Inventory;
import com.pos.app.model.Item;
import com.pos.app.model.Location;
import com.pos.app.model.InventoryModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class ItemStore {
    public static final MapProperty<String, List<Item>> itemsPerLocation = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));

    public static final ListProperty<Item> visibleItems = new SimpleListProperty<>(FXCollections.synchronizedObservableList(FXCollections.observableList(new ArrayList<>())));

    public static final ListProperty<Location> locations = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));

    public static Location currentLocation;

    public static final MapProperty<Integer, List<Inventory>> inventories = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));

    public static final IntegerProperty pageSize = new SimpleIntegerProperty(20);

    public static final IntegerProperty pageCount = new SimpleIntegerProperty(0);

    public static final IntegerProperty currentPage = new SimpleIntegerProperty();

    static {
        locations.get().add(new Location(1, "Default Location", false));

        currentLocation = new Location(0, "", false);

        InventoryModel inventoryModel = InventoryModel.getInstance();
        ObservableList<Item> itemsFromModel = inventoryModel.getItems();

        // Khởi tạo itemsPerLocation cho tất cả các vị trí
        for (Location location : locations.get()) {
            itemsPerLocation.put(location.getName().get(), new ArrayList<>());
        }

        // Đồng bộ items từ InventoryModel vào itemsPerLocation
        itemsFromModel.addListener((ListChangeListener<Item>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Item addedItem : change.getAddedSubList()) {
                        // Thêm vào vị trí hiện tại
                        String currentLocationName = currentLocation.getName().getValue();
                        if (currentLocationName != null && !currentLocationName.isEmpty()) {
                            List<Item> locationItems = itemsPerLocation.get(currentLocationName);
                            if (locationItems != null) {
                                locationItems.add(addedItem);
                                System.out.println("Added item to " + currentLocationName + ": " + addedItem.getItemName().get());
                            }
                        }
                    }
                } else if (change.wasRemoved()) {
                    for (Item removedItem : change.getRemoved()) {
                        String currentLocationName = currentLocation.getName().getValue();
                        if (currentLocationName != null && !currentLocationName.isEmpty()) {
                            List<Item> locationItems = itemsPerLocation.get(currentLocationName);
                            if (locationItems != null) {
                                locationItems.remove(removedItem);
                                System.out.println("Removed item from " + currentLocationName + ": " + removedItem.getItemName().get());
                            }
                        }
                    }
                }
            }
            updateVisibleItems();
        });

        currentLocation.getName().addListener((observable, oldValue, newValue) -> {
            System.out.println("Location changed to: " + newValue);
            updateVisibleItems();
        });

        currentPage.addListener((observable, oldValue, newValue) -> {
            System.out.println("Page changed to: " + newValue);
            updateVisibleItems();
        });

        currentLocation.getName().set(locations.getFirst().getName().get());

        inventories.put(1, new ArrayList<>());
        inventories.put(2, new ArrayList<>());
        inventories.put(3, new ArrayList<>());
    }

    public static void updateVisibleItems() {
        visibleItems.clear();
        String currentLocationName = currentLocation.getName().getValue();
        if (currentLocationName != null && !currentLocationName.isEmpty()) {
            itemsPerLocation.computeIfPresent(currentLocationName, (key, value) -> {
                pageCount.set((int) Math.ceil((double) value.size() / ItemStore.pageSize.getValue()));
                int fromIndex = currentPage.get() * ItemStore.pageSize.getValue();
                int toIndex = Math.min(fromIndex + ItemStore.pageSize.getValue(), value.size());
                visibleItems.addAll(value.subList(fromIndex, toIndex));
                System.out.println("Updated visible items for " + currentLocationName + ": " + visibleItems.size() + " items");
                return value;
            });
        }
    }
}