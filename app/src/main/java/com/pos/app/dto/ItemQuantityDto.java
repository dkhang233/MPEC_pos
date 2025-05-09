package com.pos.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemQuantityDto {
    private int itemId;
    private int changedQuantity;
    private String comment;
}
