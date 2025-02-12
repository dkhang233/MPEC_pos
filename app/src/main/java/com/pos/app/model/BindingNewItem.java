package com.pos.app.model;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.Data;

@Data
public class BindingNewItem {
    private final StringProperty barcode = new SimpleStringProperty("");
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty category = new SimpleStringProperty("");
    private final StringProperty supplier = new SimpleStringProperty("");
    private final DoubleProperty wholesalePrice = new SimpleDoubleProperty();
    private final DoubleProperty retailPrice = new SimpleDoubleProperty();
    private final DoubleProperty tax = new SimpleDoubleProperty();
    private final IntegerProperty stockQuantity = new SimpleIntegerProperty();
    private final IntegerProperty receivingQuantity = new SimpleIntegerProperty();
    private final IntegerProperty reorderLevel = new SimpleIntegerProperty();
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty avatar = new SimpleStringProperty("");
    private final ListProperty<String> stockTypes = new SimpleListProperty<>(FXCollections.observableArrayList("Stock", "Non-stock"));
    private final ObjectProperty<String> selectedStockType = new SimpleObjectProperty<>("Stock");
    private final ListProperty<String> itemTypes = new SimpleListProperty<>(FXCollections.observableArrayList("Standard", "Kit"));
    private final ObjectProperty<String> selectedItemType = new SimpleObjectProperty<>("Standard");
    private final BooleanProperty deleted = new SimpleBooleanProperty(false);


    public Item mapToItem(){
        return Item.builder()
                .itemNumber(barcode.get())
                .name(name.get())
                .category(category.get())
                .supplier(supplier.get())
                .wholesalePrice(wholesalePrice.get())
                .retailPrice(retailPrice.get())
                .taxPercent(tax.get())
                .quantity(stockQuantity.get())
                .avatar(avatar.get())
                .build();
    }
}
