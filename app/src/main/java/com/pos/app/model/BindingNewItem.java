package com.pos.app.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.Data;

@Data
public class BindingNewItem {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty("");
    private final StringProperty barcode = new SimpleStringProperty("");
    private final StringProperty category = new SimpleStringProperty("");
    private final StringProperty supplier = new SimpleStringProperty("");
    private final DoubleProperty wholesalePrice = new SimpleDoubleProperty(0.0);
    private final DoubleProperty retailPrice = new SimpleDoubleProperty(0.0);
    private final StringProperty tax1Name = new SimpleStringProperty("");
    private final DoubleProperty tax1 = new SimpleDoubleProperty(0.0);
    private final StringProperty tax2Name = new SimpleStringProperty("");
    private final DoubleProperty tax2 = new SimpleDoubleProperty(0.0);
    private final StringProperty hsnCode = new SimpleStringProperty("");
    private final DoubleProperty stockQuantity = new SimpleDoubleProperty(0.0);
    private final IntegerProperty receivingQuantity = new SimpleIntegerProperty(0);
    private final IntegerProperty reorderLevel = new SimpleIntegerProperty(0);
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty avatar = new SimpleStringProperty("");
    private final BooleanProperty allowAlternateDescription = new SimpleBooleanProperty(false);
    private final BooleanProperty hasSerialNumber = new SimpleBooleanProperty(false);
    private final BooleanProperty deleted = new SimpleBooleanProperty(false);



    public Item mapToItem(){
        return Item.builder()
                .name(name.get())
                .barcode(barcode.get())
                .category(category.get())
                .supplier(supplier.get())
                .wholesalePrice(wholesalePrice.get())
                .retailPrice(retailPrice.get())
                .tax1Name(tax1Name.get())
                .tax1(tax1.get())
                .tax2Name(tax2Name.get())
                .tax2(tax2.get())
                .hsnCode(hsnCode.get())
                .receivingQuantity(receivingQuantity.get())
                .stockQuantity(stockQuantity.get())
                .reorderLevel(reorderLevel.get())
                .description(description.get())
                .avatar(avatar.get())
                .allowAlternateDescription(allowAlternateDescription.get())
                .hasSerialNumber(hasSerialNumber.get())
                .deleted(deleted.get())
                .build();
    }
}
