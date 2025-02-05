package com.pos.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Supplier {
    private int id;
    private String companyName;
    private String agencyName;
    private String category;
    private String firstName;
    private String lastName;
    private String Email;
    private String phoneNumber;
}
