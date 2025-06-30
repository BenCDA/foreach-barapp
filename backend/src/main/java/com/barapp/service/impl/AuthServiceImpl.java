package com.barapp.service.impl;

import com.barapp.dto.LoginRequest;
import com.barapp.dto.RegisterRequest;
import com.barapp.dto.UserResponse;
import com.barapp.model.User;
import com.barapp.repository.UserRepository;
import com.barapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository       userRepository;
    private final PasswordEncoder      passwordEncoder;
    private final AuthenticationManager authManager;

    @Override
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already in use");
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(req.getRole());
        userRepository.save(u);
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }

    @Override
    public String login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        // pour l'instant on renvoie juste "OK"
        return auth.isAuthenticated() ? "OK" : "FAIL";
    }
}
