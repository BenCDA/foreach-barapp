package com.barapp.service;

import com.barapp.config.security.JwtTokenProvider;
import com.barapp.dto.LoginRequest;
import com.barapp.dto.LoginResponse;
import com.barapp.dto.RegisterRequest;
import com.barapp.dto.UserResponse;
import com.barapp.model.User;
import com.barapp.repository.UserRepository;
import com.barapp.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.barapp.model.User.Role.CLIENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationManager = mock(AuthenticationManager.class);
        jwtTokenProvider = mock(JwtTokenProvider.class);

        authService = new AuthServiceImpl(userRepository, passwordEncoder, authenticationManager, jwtTokenProvider);
    }

    @Test
    void login_shouldReturnToken() {
        LoginRequest request = new LoginRequest("ben@test.com", "pwd");
        Authentication auth = new UsernamePasswordAuthenticationToken("ben@test.com", "pwd");
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        User user = new User(1L, "Ben", "ben@test.com", "pwd", CLIENT);
        when(userRepository.findByEmail("ben@test.com")).thenReturn(Optional.of(user));
        when(jwtTokenProvider.generateToken(user)).thenReturn("jwt");

        LoginResponse response = authService.login(request);

        assertEquals("jwt", response.getToken());
    }

    @Test
    void register_shouldReturnUserResponse() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Ben");
        request.setEmail("ben@test.com");
        request.setPassword("pwd");
        request.setRole(CLIENT);

        when(passwordEncoder.encode("pwd")).thenReturn("encoded");
        User saved = new User(1L, "Ben", "ben@test.com", "encoded", CLIENT);
        when(userRepository.save(any(User.class))).thenReturn(saved);

        UserResponse resp = authService.register(request);

        assertEquals("Ben", resp.getName());
        assertEquals("ben@test.com", resp.getEmail());
        verify(passwordEncoder).encode("pwd");
    }
}
