package com.pos.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Expenses {
    private int id;
    private int date;
    private Supplier supplier;
    private String taxCode;
    private double amount;
    private double tax;
    private String paymentType;
    private String categoryName;
    private String description;
    private String createdBy;
}
