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
    public static final ListProperty<Item> items = new SimpleListProperty<>(FXCollections.synchronizedObservableList(FXCollections.observableList(new ArrayList<>())));

    public static final ListProperty<Item> visibleItems = new SimpleListProperty<>(FXCollections.synchronizedObservableList(FXCollections.observableList(new ArrayList<>())));
    
    public static final MapProperty<Integer, List<Inventory>> inventories = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));

    public static final IntegerProperty pageSize = new SimpleIntegerProperty(20);

    public static final IntegerProperty pageCount = new SimpleIntegerProperty(0);

    public static final IntegerProperty currentPage = new SimpleIntegerProperty();
    

    public static void updateVisibleItems() {
        visibleItems.clear();
        pageCount.set((int) Math.ceil((double) items.size() / ItemStore.pageSize.getValue()));
        int fromIndex = currentPage.get() * ItemStore.pageSize.getValue();
        int toIndex = Math.min(fromIndex + ItemStore.pageSize.getValue(), items.size());
        visibleItems.addAll(items.subList(fromIndex, toIndex));
        System.out.println("Updated visible items: visibleItems.size()");
    }
}