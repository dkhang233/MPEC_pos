package com.pos.backend.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "item")
    private Integer item;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "changed_quantity")
    private Integer changedQuantity;

    @Column(name = "after_quantity")
    private Integer afterQuantity;

    @Column(name = "comment")
    private String comment;
}
