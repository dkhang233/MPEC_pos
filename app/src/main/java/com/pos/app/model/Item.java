package com.pos.app.model;

import javafx.beans.property.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty itemName = new SimpleStringProperty("");
    private StringProperty barcode = new SimpleStringProperty("");
    private StringProperty category = new SimpleStringProperty("");
    private StringProperty supplier = new SimpleStringProperty("");
    private DoubleProperty costPrice = new SimpleDoubleProperty(0);
    private DoubleProperty sellingPrice = new SimpleDoubleProperty(0);
    private IntegerProperty quantity = new SimpleIntegerProperty(0);
    private IntegerProperty reorderLevel = new SimpleIntegerProperty(0);
    private StringProperty description = new SimpleStringProperty("");
    private StringProperty avatar = new SimpleStringProperty("");
    private BooleanProperty deleted = new SimpleBooleanProperty(false);

    // Chuyển từ Item sang BindingNewItem
    public BindingNewItem mapToBindingNewItem() {
        BindingNewItem bindingNewItem = new BindingNewItem();
        bindingNewItem.getId().set(id.get());
        bindingNewItem.getItemName().set(itemName.get());
        bindingNewItem.getBarcode().set(barcode.get());
        bindingNewItem.getCategory().set(category.get());
        bindingNewItem.getSupplier().set(supplier.get());
        bindingNewItem.getCostPrice().set(costPrice.get());
        bindingNewItem.getSellingPrice().set(sellingPrice.get());
        bindingNewItem.getQuantity().set(quantity.get());
        bindingNewItem.getReorderLevel().set(reorderLevel.get());
        bindingNewItem.getDescription().set(description.get());
        bindingNewItem.getAvatar().set(avatar.get());
        bindingNewItem.getDeleted().set(deleted.get());
        return bindingNewItem;
    }

    // Sao chép dữ liệu từ item khác
    public void copyFromOtherItem(Item item) {
        id.set(item.getId().get());
        itemName.set(item.getItemName().get());
        barcode.set(item.getBarcode().get());
        category.set(item.getCategory().get());
        supplier.set(item.getSupplier().get());
        quantity.set(item.getQuantity().get());
        costPrice.set(item.getCostPrice().get());
        sellingPrice.set(item.getSellingPrice().get());
        reorderLevel.set(item.getReorderLevel().get());
        description.set(item.getDescription().get());
        avatar.set(item.getAvatar().get());
        deleted.set(item.getDeleted().get());
    }

    public static Receivings mapToReceivings(Item item) {
        Receivings receivings = new Receivings(
                item.getId().get(),
                item.getBarcode().get(),
                item.getItemName().get(),
                item.getCategory().get(),
                item.getSupplier().get(),
                item.getCostPrice().get(),
                item.getSellingPrice().get(),
                item.getQuantity().get(),
                item.getAvatar().get());

        return receivings;

    }

    public static Sales mapToSales(Item item) {
        Sales receivings = new Sales(
                item.getId().get(),
                item.getBarcode().get(),
                item.getItemName().get(),
                item.getCategory().get(),
                item.getSupplier().get(),
                item.getCostPrice().get(),
                item.getSellingPrice().get(),
                item.getQuantity().get(),
                item.getAvatar().get());

        return receivings;

    }

}
