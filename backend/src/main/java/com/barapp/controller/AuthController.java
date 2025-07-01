package com.barapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barapp.dto.LoginRequest;
import com.barapp.dto.LoginResponse;
import com.barapp.dto.RegisterRequest;
import com.barapp.dto.UserResponse;
import com.barapp.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req);
        return ResponseEntity.ok(new LoginResponse(token));
    }

     @PostMapping("/register")
    public ResponseEntity<Void> register(
        @Valid @RequestBody RegisterRequest req
    ) {
        authService.register(req);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/me")
public ResponseEntity<UserResponse> whoAmI(Authentication auth) {
    String email = auth.getName();
    UserResponse user = authService.getByEmail(email);
    return ResponseEntity.ok(user);
}

}
