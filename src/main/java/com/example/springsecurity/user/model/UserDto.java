package com.example.springsecurity.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDto {

    @AllArgsConstructor
    @Getter
    public static class SignupRequest {
        private String name;
        private String email;
        private String password;

        public User toEntity(PasswordEncoder passwordEncoder) {
            return User.builder()
                    .name(this.name)
                    .email(this.email)
                    .password(passwordEncoder.encode(this.password))
                    .build();
        }
    }

    @AllArgsConstructor
    @Getter
    public static class LoginRequest {
        private String email;
        private String password;
    }
}
