package com.pos.app.dto;

import com.pos.app.model.Item;
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
    
    private int supplier;

    private String barcode;

    private String description;

    private Double costPrice;

    private Double sellingPrice;

    private Integer reorderLevel;

    private String picFilename;

    private Boolean deleted = false;

    public Item mapToItem() {
        Item item = new Item();
        item.getId().set(this.itemId);
        item.getItemName().set(this.name);
        item.getCategory().set(this.category);
        item.getSupplier().set(String.valueOf(this.supplier));
        item.getBarcode().set(this.barcode);
        item.getDescription().set(this.description);
        item.getWholesalePrice().set(this.costPrice);
        item.getRetailPrice().set(this.sellingPrice);
        item.getReorderLevel().set(this.reorderLevel);
        item.getAvatar().set(this.picFilename);
        item.getDeleted().set(this.deleted);
        return item;
    }
}
