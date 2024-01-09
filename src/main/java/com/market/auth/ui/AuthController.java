package com.market.auth.ui;

import com.market.auth.application.AuthService;
import com.market.auth.application.dto.LoginRequest;
import com.market.auth.application.dto.SignupRequest;
import com.market.auth.ui.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody final SignupRequest request) {
        String token = authService.signup(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody final LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@AuthMember final Long id) {
        String result = authService.test(id);
        return ResponseEntity.ok(result);
    }
}
