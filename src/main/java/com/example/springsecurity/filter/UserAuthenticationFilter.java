package com.example.springsecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // UsernamePasswordAuthenticationFilter는 AuthenticationManager를 의존성 주입 받아야한다.
    public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    // 인증 요청을 받으면 실행되는 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 1. 인증 요청 파싱
        // 2. UsernamePasswordAuthenticationToken 생성
        // 3. AuthenticationManager로 인증 위임
        return super.attemptAuthentication(request, response);
    }

    // 인증에 성공하면 실행되는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 1. jwt를 만든다.
        // 2. 1에서 만든 jwt를 쿠키에 담아 응답을 보낸다.
        super.successfulAuthentication(request, response, chain, authResult);
    }

    // 인증에 실패하면 실행되는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
