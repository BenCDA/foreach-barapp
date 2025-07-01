package com.barapp.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.barapp.config.security.JwtTokenProvider;
import com.barapp.dto.LoginRequest;
import com.barapp.dto.RegisterRequest;
import com.barapp.dto.UserResponse;
import com.barapp.model.User;
import com.barapp.repository.UserRepository;
import com.barapp.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
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
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        if (auth.isAuthenticated()) {
            // NE PAS faire : generateToken(user)  ← c’est ce qui déclenche l’erreur
            // Bien faire :
            return jwtTokenProvider.generateToken(req.getEmail());
        }
        throw new BadCredentialsException("Invalid login");
    }

     @Override
    public UserResponse getByEmail(String email) {
        User u = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }
}
