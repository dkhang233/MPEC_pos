package com.pos.app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Supplier {
    private int id;
    private String companyName;
    private String phoneNumber;
    private String address;
    private Boolean deleted = false;
}
