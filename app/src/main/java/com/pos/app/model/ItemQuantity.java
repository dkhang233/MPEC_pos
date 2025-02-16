package com.pos.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemQuantity {
    private int itemId;

    private String locationName;

    private int quantity;
}
