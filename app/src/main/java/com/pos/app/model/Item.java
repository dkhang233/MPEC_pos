package com.pos.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private int id;
    private String name;
    private String itemNumber;
    private String category;
    private String supplier;
    private double wholesalePrice;
    private double retailPrice;
    private double quantity;
    private double taxPercent;
    private String avatar;
}
