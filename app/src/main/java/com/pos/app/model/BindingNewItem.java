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
@Builder
@NoArgsConstructor
public class BindingNewItem {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty itemName = new SimpleStringProperty("");
    private final StringProperty barcode = new SimpleStringProperty("");
    private final StringProperty category = new SimpleStringProperty("");
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
    private final IntegerProperty stockQuantity = new SimpleIntegerProperty(0);
    private final Map<String,IntegerProperty> quantitiesPerLocation = new HashMap<>();
    private final IntegerProperty receivingQuantity = new SimpleIntegerProperty(0);
    private final IntegerProperty reorderLevel = new SimpleIntegerProperty(0);
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty avatar = new SimpleStringProperty("");
    private final BooleanProperty allowAlternateDescription = new SimpleBooleanProperty(false);
    private final BooleanProperty hasSerialNumber = new SimpleBooleanProperty(false);
    private final BooleanProperty deleted = new SimpleBooleanProperty(false);



    public Item mapToItem(){
        return Item.builder()
                .name(itemName.get())
                .barcode(barcode.get())
                .category(category.get())
                .stockType(selectedStockType.get())
                .itemType(selectedItemType.get())
                .supplier(supplier.get())
                .wholesalePrice(wholesalePrice.get())
                .itemType(selectedItemType.get())
                .stockType(selectedStockType.get())
                .retailPrice(retailPrice.get())
                .tax1Name(tax1Name.get())
                .tax1(tax1.get())
                .tax2Name(tax2Name.get())
                .tax2(tax2.get())
                .quantityPerLocation(quantitiesPerLocation.entrySet().stream().map(entry -> ItemQuantity.builder().locationName(entry.getKey()).quantity(entry.getValue().get()).build()).toList())
                .hsnCode(hsnCode.get())
                .receivingQuantity(receivingQuantity.get())
                .reorderLevel(reorderLevel.get())
                .description(description.get())
                .avatar(avatar.get())
                .allowAlternateDescription(allowAlternateDescription.get())
                .hasSerialNumber(hasSerialNumber.get())
                .deleted(deleted.get())
                .build();
    }

    public void mapFromItem(Item item){
        id.set(item.getId());
        itemName.set(item.getName());
        barcode.set(item.getBarcode());
        category.set(item.getCategory());
        supplier.set(item.getSupplier());
        wholesalePrice.set(item.getWholesalePrice());
        selectedItemType.set(item.getItemType());
        selectedStockType.set(item.getStockType());
        retailPrice.set(item.getRetailPrice());
        tax1Name.set(item.getTax1Name());
        tax1.set(item.getTax1());
        tax2Name.set(item.getTax2Name());
        tax2.set(item.getTax2());
        hsnCode.set(item.getHsnCode());
        receivingQuantity.set(item.getReceivingQuantity());
        stockQuantity.set(item.getQuantityAtCurrentLocation().getQuantity());
        reorderLevel.set(item.getReorderLevel());
        description.set(item.getDescription());
        avatar.set(item.getAvatar());
        allowAlternateDescription.set(item.isAllowAlternateDescription());
        hasSerialNumber.set(item.isHasSerialNumber());
        deleted.set(item.isDeleted());
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
        stockQuantity.set(0);
        reorderLevel.set(0);
        description.set("");
        avatar.set("");
        allowAlternateDescription.set(false);
        hasSerialNumber.set(false);
        deleted.set(false);
    }
}