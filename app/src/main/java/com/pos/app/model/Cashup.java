package com.pos.app.model;

import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class Cashup {
    private int id;
    private DateTimeFormatter openedDate;
    private String openedBy;
    private double openCash;
    private double in_outCash;
    private DateTimeFormatter closedDate;
    private String closedBy;
    private double closedCash;
    private Boolean notes;
    private double dues;
    private double cards;
    private double checks;
    private double totals;
}
