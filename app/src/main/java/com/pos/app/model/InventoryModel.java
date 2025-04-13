package com.pos.app.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InventoryModel {
    private static InventoryModel instance;
    private ObservableList<Receivings> receivings;
    private ObservableList<Item> items;

    private InventoryModel() {
        receivings = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList();
    }

    public static InventoryModel getInstance() {
        if (instance == null) {
            instance = new InventoryModel();
        }
        return instance;
    }

    public ObservableList<Receivings> getReceivings() {
        return receivings;
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public void addReceiving(Receivings receiving) {
        receivings.add(receiving);
        updateItemFromReceiving(receiving);
    }

    private void updateItemFromReceiving(Receivings receiving) {
        Item existingItem = items.stream()
                .filter(item -> item.getBarcode().get().equals(receiving.getBarcode()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.getQuantity().set(existingItem.getQuantity().get() + receiving.getQuantity());
        } else {
            Item newItem = new Item();
            newItem.getId().set(receiving.getId());
            newItem.getBarcode().set(receiving.getBarcode());
            newItem.getItemName().set(receiving.getName());
            newItem.getCategory().set(receiving.getCategory());
            newItem.getSupplier().set(receiving.getSupplier());
            newItem.getWholesalePrice().set(receiving.getWholesalePrice());
            newItem.getRetailPrice().set(receiving.getRetailPrice());
            newItem.getQuantity().set(receiving.getQuantity());
            newItem.getAvatar().set(receiving.getAvatar());
            items.add(newItem);
        }
    }
}