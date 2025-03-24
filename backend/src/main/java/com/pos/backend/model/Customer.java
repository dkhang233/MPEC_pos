package com.pos.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
public class Customer {

    @Id
    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "account_number")
    private String accountNumber = "";

    @Column(name = "discount")
    private Double discount;

    @Column(name = "discount_type")
    private int discountType;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "date")
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "consent")
    private Integer consent = 0;
}
