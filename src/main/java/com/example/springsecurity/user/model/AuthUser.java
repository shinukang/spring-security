package com.example.springsecurity.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder()
@Getter
@AllArgsConstructor
public class AuthUser implements UserDetails {
    private String email;
    private String password;
    private String role;
    private Boolean enable;

    public static AuthUser fromEntity(User entity) {
        return AuthUser.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .enable(entity.getEnable())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
