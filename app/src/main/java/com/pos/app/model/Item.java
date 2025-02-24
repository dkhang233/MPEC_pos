package com.pos.app.model;

import com.pos.app.store.ItemStore;
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
    private int id;
    private String itemName;
    private String barcode;
    private String category;
    private HashMap<String, ?> attributes ;
    private String stockType;
    private String itemType;
    private String supplier;
    private double wholesalePrice;
    private double retailPrice;
    private String tax1Name;
    private double tax1;
    private String tax2Name;
    private double tax2;
    private String hsnCode;
    private List<ItemQuantity> quantityPerLocation = new ArrayList<>();
    private int receivingQuantity;
    private int reorderLevel;
    private String description;
    private String avatar;
    private boolean allowAlternateDescription;
    private boolean hasSerialNumber;
    private boolean deleted;

    public Item( String barcode, String itemName) {
        this.itemName = itemName;
        this.barcode = barcode;
    }

    public ItemQuantity getQuantityAtCurrentLocation(){
        return quantityPerLocation.stream()
                .filter(quantity -> quantity.getLocationName().equals(ItemStore.currentLocation))
                .findFirst()
                .orElse(new ItemQuantity(0, "", 0));
    }
}
