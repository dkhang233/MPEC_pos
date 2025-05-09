package com.pos.app.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.app.dto.LoginDto;
import com.pos.app.model.Login;

public class UserApi extends BaseApi {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Boolean login(LoginDto loginDto) {
        String response = request("/login", "POST", loginDto);
        try {
            Boolean res = objectMapper.readValue(response, Boolean.class);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
