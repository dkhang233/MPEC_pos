package com.pos.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private int id;
    private int item;
    private int user;
    private LocalDate timestamp;
    private String comment;
    private String location;
    private int inventory;  // Số lượng cập nhật
    private int afterInventory; // Số lượng tồn sau khi cập nhật
}
