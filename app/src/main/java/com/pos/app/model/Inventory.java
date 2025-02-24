package com.pos.app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder

public class Inventory {
    private int id;
    private int items;
    private int users;
    private LocalDate timestamp;
    private String comment;
    private Location location;
    private int inventory;
}
