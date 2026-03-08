package com.example.springsecurity.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Builder.Default
    private String role = "ROLE_USER";
    @Builder.Default
    private Boolean enable = true;
}
