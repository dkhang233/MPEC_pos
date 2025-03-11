package com.pos.backend.entity;

import jakarta.persistence.*;
import lombok.*;


@Builder
@Entity
@Table(name = "items")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

public class Item {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "item_id")
 private Integer id;

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

 @Column(name = "unit_price")
 private Double unitPrice;

 @Column(name = "reorder_level")
 private Double reorderLevel ;

 @Column(name = "receiving_quantity")
 private Double receivingQuantity;

 @Column(name = "pic_filename")
 private String picFilename;

 @Column(name = "stock_type")
 private Boolean stockType = false;

 @Column(name = "item_type")
 private Boolean itemType = false;

 @Column(name = "deleted")
 private Boolean deleted = false;

 @Column(name = "qty_per_pack")
 private Double qtyPerPack;

 @Column(name = "pack_name")
 private String packName = "Each";
}
