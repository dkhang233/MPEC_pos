package com.pos.app.store;


import lombok.Data;

@Data
public class UserStore {
    public static String username;

    static
    {
        username = "khang";
    }
}
