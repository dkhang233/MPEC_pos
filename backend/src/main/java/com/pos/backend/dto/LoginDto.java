package com.pos.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    private String username; // Unique identifier for the user
    private String password; // Password for the user account
}
