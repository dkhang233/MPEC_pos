package com.pos.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;


    @Column(name = "supplier_id")
    private int supplier;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "description")
    private String description;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "reorder_level")
    private BigDecimal reorderLevel = BigDecimal.ZERO;

    @Column(name = "pic_filename")
    private String picFilename;

    @Column(name = "deleted")
    private Boolean deleted = false;
}
