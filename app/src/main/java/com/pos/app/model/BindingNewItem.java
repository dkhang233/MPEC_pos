package com.pos.app.model;

import com.pos.app.store.ItemStore;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


// BindingNewItem là một class dùng để binding dữ liệu giữa các trường trong form và Item
@Data
public class BindingNewItem {
    private final IntegerProperty id = new SimpleIntegerProperty(0);
    private final StringProperty itemName = new SimpleStringProperty("");
    private final StringProperty barcode = new SimpleStringProperty("");
    private final StringProperty category = new SimpleStringProperty("");
    private  final ListProperty<String> suppliers = new SimpleListProperty<>(
            FXCollections.observableArrayList(new ArrayList<>()));
    private final StringProperty supplier = new SimpleStringProperty("");
    private final DoubleProperty costPrice = new SimpleDoubleProperty(0.0);
    private final DoubleProperty sellingPrice = new SimpleDoubleProperty(0.0);
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);
    private final IntegerProperty reorderLevel = new SimpleIntegerProperty(0);
    private final StringProperty description = new SimpleStringProperty("");
    private final StringProperty avatar = new SimpleStringProperty("");
    private final BooleanProperty deleted = new SimpleBooleanProperty(false);

    public BindingNewItem(){
        List supplierName = new ArrayList<>();
        ItemStore.suppliers.getValue().forEach(supplier -> {
            supplierName.add(supplier.getCompanyName());
        });
        this.suppliers.addAll(supplierName);
        this.suppliers.add("Không có");
    }


    // Chuyển từ BindingNewItem sang Item
    public Item mapToItem(){
        Item item = new Item();
        item.getId().set(id.getValue());
        item.getItemName().set(itemName.getValue());
        item.getBarcode().set(barcode.getValue());
        item.getCategory().set(category.getValue());
        item.getSupplier().set(supplier.getValue());
        item.getReorderLevel().set(reorderLevel.getValue());
        item.getCostPrice().set(costPrice.getValue());
        item.getSellingPrice().set(sellingPrice.getValue());
        item.getQuantity().set(quantity.getValue());
        item.getDescription().set(description.getValue());
        item.getAvatar().set(avatar.getValue());
        item.getDeleted().set(deleted.getValue());
        return item;
    }
}