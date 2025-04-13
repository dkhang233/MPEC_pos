package com.pos.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
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
    private Double costPrice;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "reorder_level")
    private Integer reorderLevel;

    @Column(name = "pic_filename")
    private String picFilename;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "owned_by")
    private String ownedBy;
}
