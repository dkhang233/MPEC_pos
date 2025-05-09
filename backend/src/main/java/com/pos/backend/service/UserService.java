package com.pos.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pos.backend.dto.LoginDto;
import com.pos.backend.model.User;
import com.pos.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Boolean login(LoginDto loginDto) {
        Optional<User> res = userRepository.findById(loginDto.getUsername());
        if (res.isEmpty()) {
            return false;
        } else {
            User user = res.get();
            if (user.getPassword().equals(loginDto.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
}
