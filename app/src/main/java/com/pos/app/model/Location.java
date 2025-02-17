package com.pos.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Location {
    private int id;
    private String name;
    private boolean deleted;
}

