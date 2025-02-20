package com.pos.app.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

import java.util.stream.Collectors;

@Data
public class BindingUpdateInventory {
    private final StringProperty barcode = new SimpleStringProperty();
    private final StringProperty itemName = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final ListProperty<String> stockLocation = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ObjectProperty<String> selectedStockLocation = new SimpleObjectProperty<>();
    private final IntegerProperty currentQuantity = new SimpleIntegerProperty();
    private final IntegerProperty inventoryToAddOrSubtract = new SimpleIntegerProperty();
    private final StringProperty comment = new SimpleStringProperty("");
    private final ObservableList<Location> locations = FXCollections.observableArrayList();



    public BindingUpdateInventory() {
        locations.add(new Location(1,"abc",false));
        stockLocation.getValue().add("abc");
    }


//    public Inventory mapToUpdateInventory(Item item){
//        ItemQuantity itemQuantity = item.getQuantityPerLocation().stream()
//                .filter(quantity -> quantity.getLocationName().equals(selectedStockLocation.get()))
//                .findFirst()
//                .orElse(new ItemQuantity(0, "", 0));
//        itemQuantity.setQuantity(itemQuantity.getQuantity() + inventoryToAddOrSubtract.get());
//        item.getQuantityPerLocation().removeIf(quantity -> quantity.getLocationName().equals(selectedStockLocation.get()));
//        item.getQuantityPerLocation().add(itemQuantity);
//        return Inventory.builder()
//                .location(
//                        locations.stream()
//                                .filter(location -> location.getName().equals(selectedStockLocation.get()))
//                                .findFirst()
//                                .orElse(new Location(0, "", false)))
//                .inventory(inventoryToAddOrSubtract.get())
//                .comment(comment.get())
//                .build();
//    }

    public void mapFromItem(Item item){
        barcode.set(item.getBarcode());
        itemName.set(item.getItemName());
        category.set(item.getCategory());
        stockLocation.clear();
        stockLocation.addAll(item.getQuantityPerLocation().stream()
                .map(ItemQuantity::getLocationName)
                .toList());
        selectedStockLocation.set(stockLocation.get(0));
        currentQuantity.set(item.getQuantityAtCurrentLocation().getQuantity());
    }
}
