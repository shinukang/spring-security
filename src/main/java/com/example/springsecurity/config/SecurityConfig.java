package com.example.springsecurity.config;

import com.example.springsecurity.filter.UserAuthenticationFilter;
import com.example.springsecurity.filter.UserAuthorizationFilter;
import com.example.springsecurity.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

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

        httpSecurity.addFilterBefore(new UserAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAt(new UserAuthenticationFilter(authenticationManager, objectMapper, jwtUtil), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
