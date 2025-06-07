package com.pos.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suppliers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "company_name")
    private String companyName = "Unknown";

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "owned_by")
    private String ownedBy;

    @Column(name = "address")
    private String address;
}
