package com.pos.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String username; // Unique identifier for the user

    private String password; // Password for the user account

    private String role; // Role of the user (e.g., admin, cashier, etc.)

    private String email; // Email address of the user

    @Column(name = "managed_by")
    private String managedBy; // Phone number of the user

    private Boolean deleted; // Flag to indicate if the user is deleted
}
