package com.pos.app.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.pos.app.model.Inventory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {
    private Integer id;
    private Integer item;
    private LocalDateTime timestamp;
    private Integer changedQuantity;
    private Integer afterQuantity;
    private String comment;

    public Inventory mapToInventory() {
        Inventory inventory = new Inventory();
        inventory.getId().set(this.id);
        inventory.getItem().set(this.item);
        inventory.getTimestamp().set(this.timestamp);
        inventory.getChangedQuantity().set(this.changedQuantity);
        inventory.getAfterQuantity().set(this.afterQuantity);
        inventory.getComment().set(this.comment);
        return inventory;
    }
}
