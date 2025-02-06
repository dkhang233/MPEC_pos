package com.pos.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String totalSpent;
}
