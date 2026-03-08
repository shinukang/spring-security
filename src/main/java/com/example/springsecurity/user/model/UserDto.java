package com.example.springsecurity.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDto {

    @AllArgsConstructor
    @Getter
    public static class LoginRequest {
        private String email;
        private String password;
    }
}
