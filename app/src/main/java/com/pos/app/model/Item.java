package com.pos.app.model;

import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty itemName = new SimpleStringProperty("");
    private StringProperty barcode = new SimpleStringProperty("");
    private StringProperty category = new SimpleStringProperty("");
//    private ObjectProperty<HashMap<String, ?>> attributes = new SimpleObjectProperty<>();
    private StringProperty stockType = new SimpleStringProperty("Stock");
    private StringProperty itemType = new SimpleStringProperty("Standard");
    private StringProperty supplier = new SimpleStringProperty("");
    private DoubleProperty wholesalePrice = new SimpleDoubleProperty(0);
    private DoubleProperty retailPrice = new SimpleDoubleProperty(0);
    private StringProperty tax1Name = new SimpleStringProperty("");
    private DoubleProperty tax1 = new SimpleDoubleProperty(0);
    private StringProperty tax2Name = new SimpleStringProperty("");
    private DoubleProperty tax2 = new SimpleDoubleProperty(0);
    private StringProperty hsnCode = new SimpleStringProperty("");
    private IntegerProperty quantityAtCurrentLocation = new SimpleIntegerProperty(0);
    private IntegerProperty receivingQuantity = new SimpleIntegerProperty(0);
    private IntegerProperty reorderLevel = new SimpleIntegerProperty(0);
    private StringProperty description = new SimpleStringProperty("");
    private StringProperty avatar = new SimpleStringProperty("");
    private BooleanProperty allowAlternateDescription = new SimpleBooleanProperty(false);
    private BooleanProperty hasSerialNumber = new SimpleBooleanProperty(false);
    private BooleanProperty deleted = new SimpleBooleanProperty(false);



    public void setQuantityAtCurrentLocation(int quantityAtCurrentLocation) {
        this.quantityAtCurrentLocation.set(quantityAtCurrentLocation);
    }

    // Chuyển từ Item sang BindingNewItem
    public BindingNewItem mapToBindingNewItem() {
        BindingNewItem bindingNewItem = new BindingNewItem();
        bindingNewItem.getId().set(id.get());
        bindingNewItem.getItemName().set(itemName.get());
        bindingNewItem.getBarcode().set(barcode.get());
        bindingNewItem.getCategory().set(category.get());
//        bindingNewItem.getAttributes().set(attributes.get());
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
        bindingNewItem.getReceivingQuantity().set(receivingQuantity.get());
        bindingNewItem.getReorderLevel().set(reorderLevel.get());
        bindingNewItem.getDescription().set(description.get());
        bindingNewItem.getAvatar().set(avatar.get());
        bindingNewItem.getAllowAlternateDescription().set(allowAlternateDescription.get());
        bindingNewItem.getHasSerialNumber().set(hasSerialNumber.get());
        bindingNewItem.getDeleted().set(deleted.get());
        return bindingNewItem;
    }


    // Sao chép dữ liệu từ item khác
    public void copyFromOtherItem(Item item){
        id.set(item.getId().get());
        itemName.set(item.getItemName().get());
        barcode.set(item.getBarcode().get());
        category.set(item.getCategory().get());
//        attributes.set(item.getAttributes().get());
        stockType.set(item.getStockType().get());
        itemType.set(item.getItemType().get());
        supplier.set(item.getSupplier().get());
        wholesalePrice.set(item.getWholesalePrice().get());
        retailPrice.set(item.getRetailPrice().get());
        tax1Name.set(item.getTax1Name().get());
        tax1.set(item.getTax1().get());
        tax2Name.set(item.getTax2Name().get());
        tax2.set(item.getTax2().get());
        hsnCode.set(item.getHsnCode().get());
        quantityAtCurrentLocation.set(item.getQuantityAtCurrentLocation().get());
        receivingQuantity.set(item.getReceivingQuantity().get());
        reorderLevel.set(item.getReorderLevel().get());
        description.set(item.getDescription().get());
        avatar.set(item.getAvatar().get());
        allowAlternateDescription.set(item.getAllowAlternateDescription().get());
        hasSerialNumber.set(item.getHasSerialNumber().get());
        deleted.set(item.getDeleted().get());
    }


}
