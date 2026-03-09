package com.example.springsecurity.filter;

import com.example.springsecurity.user.model.AuthUser;
import com.example.springsecurity.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class UserAuthorizationFilter extends OncePerRequestFilter { // 모든 요청에 대해 실행되는 Filter
    private final JwtUtil jwtUtil;

    // 필터링하지 않을 조건을 설정한다.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 여기서는 /user로 시작하는 요청은 필터링 하지 않도록 설정
        return request.getRequestURI().startsWith("/user");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        for (Cookie cookie : request.getCookies()) {
            // 쿠키를 확인하여 ATOKEN이라는 이름을 가진 쿠키가 있다면

            if (cookie.getName().equals("ATOKEN")) {
                // 해당 값을 파싱하여
                String jwt = cookie.getValue();

                Claims claims = jwtUtil.parseToken(jwt);

                AuthUser user = AuthUser.builder()
                        .idx(claims.get("idx", Long.class))
                        .name(claims.get("name", String.class))
                        .email(claims.getSubject())
                        .role(claims.get("role", String.class))
                        .build();

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority(user.getRole()))
                );
                // SecurityContext에 넣는다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
                break;
            }
        }
        filterChain.doFilter(request, response);
    }
}
