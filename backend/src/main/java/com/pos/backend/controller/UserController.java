package com.pos.backend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.pos.backend.dto.LoginDto;
import com.pos.backend.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@Tag(name = "User API", description = "API for managing users")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public Boolean postMethodName(@RequestBody LoginDto loginInfo) {
        return userService.login(loginInfo);
    }

}
