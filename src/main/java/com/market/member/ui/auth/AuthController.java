package com.market.member.ui.auth;

import com.market.member.application.auth.AuthService;
import com.market.member.application.auth.dto.LoginRequest;
import com.market.member.application.auth.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
