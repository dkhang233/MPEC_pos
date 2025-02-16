package com.pos.app.model;

import lombok.Data;

@Data
public class ItemTax {
    private int itemId;
    private String name;
    private double percent;
}
