package com.pos.app.store;

import com.pos.app.model.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Data
public class ItemStore {
        public static final ListProperty<Item> items = new SimpleListProperty<>(
                        FXCollections.synchronizedObservableList(FXCollections.observableList(new ArrayList<>())));

        public static final ListProperty<Item> visibleItems = new SimpleListProperty<>(
                        FXCollections.synchronizedObservableList(FXCollections.observableList(new ArrayList<>())));

        public static final MapProperty<Integer, List<Inventory>> inventories = new SimpleMapProperty<>(
                        FXCollections.observableMap(new LinkedHashMap<>()));

        public static final ListProperty<Supplier> suppliers = new SimpleListProperty<>(
                        FXCollections.synchronizedObservableList(FXCollections.observableList(new ArrayList<>())));

        public static final ListProperty<Item> itemPage = new SimpleListProperty<>(
                        FXCollections.synchronizedObservableList(FXCollections.observableList(new ArrayList<>())));

        public static final IntegerProperty pageSize = new SimpleIntegerProperty(10);

        public static final IntegerProperty pageCount = new SimpleIntegerProperty(0);

        public static final IntegerProperty currentPage = new SimpleIntegerProperty();

        static {
                visibleItems.sizeProperty().addListener((observable, oldValue, newValue) -> {
                        pageCount.set((int) Math.ceil(newValue.doubleValue() / ItemStore.pageSize.getValue()));
                });

                // Khi danh sách items thay đổi thì cập nhật lại danh sách items hiển thị
                visibleItems.addListener((observable, oldValue, newValue) -> {
                        itemPage.clear();
                        int fromIndex = currentPage.get() * ItemStore.pageSize.getValue();
                        System.out.println("Current page: " + currentPage.get());
                        int toIndex = Math.min(fromIndex + ItemStore.pageSize.getValue(), visibleItems.size());
                        System.out.println("fromIndex: " + fromIndex + " toIndex: " + toIndex);
                        itemPage.addAll(visibleItems.subList(fromIndex, toIndex));
                });

                // Khi chuyển trang thì cập nhật lại danh sách item hiển thị
                currentPage.addListener((observable, oldValue, newValue) -> {
                        itemPage.clear();
                        int fromIndex = currentPage.get() * ItemStore.pageSize.getValue();
                        System.out.println("Current page: " + currentPage.get());
                        int toIndex = Math.min(fromIndex + ItemStore.pageSize.getValue(), visibleItems.size());
                        System.out.println("fromIndex: " + fromIndex + " toIndex: " + toIndex);
                        itemPage.addAll(visibleItems.subList(fromIndex, toIndex));
                });
        }
}