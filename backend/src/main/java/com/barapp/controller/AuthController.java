package com.barapp.controller;

import com.barapp.dto.LoginRequest;
import com.barapp.dto.RegisterRequest;
import com.barapp.dto.UserResponse;
import com.barapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid RegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest req) {
        return authService.login(req);
    }
}
