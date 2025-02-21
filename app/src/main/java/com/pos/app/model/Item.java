package com.pos.app.model;

import com.pos.app.store.ItemStore;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty itemName = new SimpleStringProperty();
    private StringProperty barcode = new SimpleStringProperty();
    private StringProperty category = new SimpleStringProperty();
    private ObjectProperty<HashMap<String, ?>> attributes = new SimpleObjectProperty<>();
    private StringProperty stockType = new SimpleStringProperty();
    private StringProperty itemType = new SimpleStringProperty();
    private StringProperty supplier = new SimpleStringProperty();
    private DoubleProperty wholesalePrice = new SimpleDoubleProperty();
    private DoubleProperty retailPrice = new SimpleDoubleProperty();
    private StringProperty tax1Name = new SimpleStringProperty();
    private DoubleProperty tax1 = new SimpleDoubleProperty();
    private StringProperty tax2Name = new SimpleStringProperty();
    private DoubleProperty tax2 = new SimpleDoubleProperty();
    private StringProperty hsnCode = new SimpleStringProperty();
    private ListProperty<ItemQuantity> quantityPerLocation = new SimpleListProperty<>(FXCollections.observableArrayList());
    private IntegerProperty quantityAtCurrentLocation = new SimpleIntegerProperty();
    private IntegerProperty receivingQuantity = new SimpleIntegerProperty();
    private IntegerProperty reorderLevel = new SimpleIntegerProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty avatar = new SimpleStringProperty();
    private BooleanProperty allowAlternateDescription = new SimpleBooleanProperty();
    private BooleanProperty hasSerialNumber = new SimpleBooleanProperty();
    private BooleanProperty deleted = new SimpleBooleanProperty();

    public Item() {
        quantityAtCurrentLocation.addListener();
    }

    public BindingNewItem mapToBindingNewItem() {
        BindingNewItem bindingNewItem = new BindingNewItem();
        bindingNewItem.getId().set(id.get());
        bindingNewItem.getItemName().set(itemName.get());
        bindingNewItem.getBarcode().set(barcode.get());
        bindingNewItem.getCategory().set(category.get());
        bindingNewItem.getAttributes().set(attributes.get());
        bindingNewItem.getSelectedStockType().set(stockType.get());
        bindingNewItem.getSelectedItemType().set(itemType.get());
        bindingNewItem.getSupplier().set(supplier.get());
        bindingNewItem.getWholesalePrice().set(wholesalePrice.get());
        bindingNewItem.getRetailPrice().set(retailPrice.get());
        bindingNewItem.getTax1Name().set(tax1Name.get());
        bindingNewItem.getTax1().set(tax1.get());
        bindingNewItem.getTax2Name().set(tax2Name.get());
        bindingNewItem.getTax2().set(tax2.get());
        bindingNewItem.getHsnCode().set(hsnCode.get());
        bindingNewItem.getQuantityPerLocation().setAll(quantityPerLocation.get());
        bindingNewItem.getReceivingQuantity().set(receivingQuantity.get());
        bindingNewItem.getReorderLevel().set(reorderLevel.get());
        bindingNewItem.getDescription().set(description.get());
        bindingNewItem.getAvatar().set(avatar.get());
        bindingNewItem.getAllowAlternateDescription().set(allowAlternateDescription.get());
        bindingNewItem.getHasSerialNumber().set(hasSerialNumber.get());
        bindingNewItem.getDeleted().set(deleted.get());
        return bindingNewItem;
    }
}
