package com.pos.app.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;

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




 
}
