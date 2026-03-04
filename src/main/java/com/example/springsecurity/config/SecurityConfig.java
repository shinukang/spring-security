package com.example.springsecurity.config;

import com.example.springsecurity.filter.UserAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserAuthenticationFilter userAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) {
        // 모든 경로에 대한 접근 허용
        httpSecurity.authorizeHttpRequests(
                (auth) -> auth
                        .anyRequest().permitAll()
        );
        // CSRF 방어 기능을 중지하는 코드
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        // basic 로그인 방식 사용 안하도록 설정
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);
        // form 로그인 방식 사용 안하도록 설정
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        // 커스텀 필터로 대체
        httpSecurity.addFilterAt(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
