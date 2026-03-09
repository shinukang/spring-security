package com.example.springsecurity;

import com.example.springsecurity.user.model.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @PostMapping("/user")
    public ResponseEntity user(@AuthenticationPrincipal AuthUser user) {
        log.error(user.getRole());
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/admin")
    public ResponseEntity admin(@AuthenticationPrincipal AuthUser user) {
        log.error(user.getRole());
        return ResponseEntity.ok("OK");
    }
}
