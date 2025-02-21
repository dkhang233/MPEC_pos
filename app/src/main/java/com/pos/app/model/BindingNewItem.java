package com.pos.app.model;

import javafx.beans.property.*;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.*;

@Data
@NoArgsConstructor
public class BindingNewItem {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty itemName = new SimpleStringProperty("");
    private final StringProperty barcode = new SimpleStringProperty("");
    private final StringProperty category = new SimpleStringProperty("");
    private ObjectProperty<HashMap<String, ?>> attributes = new SimpleObjectProperty<>();
    private final ListProperty<String> stockTypes = new SimpleListProperty<>(FXCollections.observableArrayList("Stock", "Non-stock"));
    private final ObjectProperty<String> selectedStockType = new SimpleObjectProperty<>();
    private final ListProperty<String> itemTypes = new SimpleListProperty<>(FXCollections.observableArrayList("Standard", "Kit"));
    private final ObjectProperty<String> selectedItemType = new SimpleObjectProperty<>("Standard");
    private final StringProperty supplier = new SimpleStringProperty("");
    private final DoubleProperty wholesalePrice = new SimpleDoubleProperty(0.0);
    private final DoubleProperty retailPrice = new SimpleDoubleProperty(0.0);
    private final StringProperty tax1Name = new SimpleStringProperty("");
    private final DoubleProperty tax1 = new SimpleDoubleProperty(0.0);
    private final StringProperty tax2Name = new SimpleStringProperty("");
    private final DoubleProperty tax2 = new SimpleDoubleProperty(0.0);
    private final StringProperty hsnCode = new SimpleStringProperty("");
    private ListProperty<ItemQuantity> quantityPerLocation = new SimpleListProperty<>(FXCollections.observableArrayList());;
    private final IntegerProperty receivingQuantity = new SimpleIntegerProperty(0);
    private final IntegerProperty reorderLevel = new SimpleIntegerProperty(0);
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty avatar = new SimpleStringProperty("");
    private final BooleanProperty allowAlternateDescription = new SimpleBooleanProperty(false);
    private final BooleanProperty hasSerialNumber = new SimpleBooleanProperty(false);
    private final BooleanProperty deleted = new SimpleBooleanProperty(false);

    public Item mapToItem(){
        Item item = new Item();
        item.getId().set(id.getValue());
        item.getItemName().set(itemName.getValue());
        item.getBarcode().set(barcode.getValue());
        item.getCategory().set(category.getValue());
        item.getAttributes().set(attributes.getValue());
        item.getStockType().set(selectedStockType.getValue());
        item.getItemType().set(selectedItemType.getValue());
        item.getSupplier().set(supplier.getValue());
        item.getWholesalePrice().set(wholesalePrice.getValue());
        item.getRetailPrice().set(retailPrice.getValue());
        item.getTax1Name().set(tax1Name.getValue());
        item.getTax1().set(tax1.getValue());
        item.getTax2Name().set(tax2Name.getValue());
        item.getTax2().set(tax2.getValue());
        item.getHsnCode().set(hsnCode.getValue());
        item.getQuantityPerLocation().setAll(quantityPerLocation);
        item.getReceivingQuantity().set(receivingQuantity.getValue());
        item.getReorderLevel().set(reorderLevel.getValue());
        item.getDescription().set(description.getValue());
        item.getAvatar().set(avatar.getValue());
        item.getAllowAlternateDescription().set(allowAlternateDescription.getValue());
        item.getHasSerialNumber().set(hasSerialNumber.getValue());
        item.getDeleted().set(deleted.getValue());
        return item;
    }

    public void clear(){
        id.set(0);
        itemName.set("");
        barcode.set("");
        category.set("");
        supplier.set("");
        wholesalePrice.set(0.0);
        selectedItemType.set("Standard");
        selectedStockType.set("Stock");
        retailPrice.set(0.0);
        tax1Name.set("");
        tax1.set(0.0);
        tax2Name.set("");
        tax2.set(0.0);
        hsnCode.set("");
        receivingQuantity.set(0);
        reorderLevel.set(0);
        description.set("");
        avatar.set("");
        allowAlternateDescription.set(false);
        hasSerialNumber.set(false);
        deleted.set(false);
    }
}