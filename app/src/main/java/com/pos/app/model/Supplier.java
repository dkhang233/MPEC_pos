package com.pos.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    private int id;
    private String companyName;
    private String phoneNumber;
    private String address;
    private String ownedBy;
    private Boolean deleted = false;
}
