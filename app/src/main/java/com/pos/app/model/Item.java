package com.pos.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int stockQuantity;
    private int receivingQuantity;
    private int reorderLevel;
    private String description;
    private String avatar;
    private boolean allowAlternateDescription;
    private boolean hasSerialNumber;
    private boolean deleted;
}
