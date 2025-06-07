package com.pos.app.dto;

import com.pos.app.model.Item;
import com.pos.app.model.Supplier;
import com.pos.app.store.ItemStore;
import com.pos.app.store.UserStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Integer itemId;

    private String name;

    private String category;

    private String supplier;

    private String barcode;

    private String description;

    private Double costPrice;

    private Double sellingPrice;

    private Integer quantity;

    private Integer reorderLevel;

    private String picFilename;

    private Boolean deleted = false;

    private String ownedBy;

    public Item mapToItem() {
        Item item = new Item();
        item.getId().set(this.itemId);
        item.getItemName().set(this.name);
        item.getCategory().set(this.category);
        item.getSupplier().set(this.supplier);
        item.getBarcode().set(this.barcode);
        item.getDescription().set(this.description);
        item.getCostPrice().set(this.costPrice);
        item.getSellingPrice().set(this.sellingPrice);
        item.getQuantity().set(this.quantity);
        item.getReorderLevel().set(this.reorderLevel);
        item.getAvatar().set(this.picFilename);
        item.getDeleted().set(this.deleted);
        return item;
    }

    public ItemDto(Item item) {
        this.itemId = item.getId().get();
        this.name = item.getItemName().get();
        this.category = item.getCategory().get();
        this.supplier = item.getSupplier().get();
        this.barcode = item.getBarcode().get();
        this.description = item.getDescription().get();
        this.costPrice = item.getCostPrice().get();
        this.sellingPrice = item.getSellingPrice().get();
        this.quantity = item.getQuantity().get();
        this.reorderLevel = item.getReorderLevel().get();
        this.picFilename = item.getAvatar().get();
        this.deleted = item.getDeleted().get();
        this.ownedBy = UserStore.username;
    }

}
