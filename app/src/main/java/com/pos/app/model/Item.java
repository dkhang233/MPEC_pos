package com.pos.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private int id;
    private String name;
    private String barcode;
    private String category;
    private String supplier;
    private double wholesalePrice;
    private double retailPrice;
    private String tax1Name;
    private double tax1;
    private String tax2Name;
    private double tax2;
    private String hsnCode;
    private double stockQuantity;
    private int receivingQuantity;
    private int reorderLevel;
    private String description;
    private String avatar;
    private boolean allowAlternateDescription;
    private boolean hasSerialNumber;
    private boolean deleted;
}
