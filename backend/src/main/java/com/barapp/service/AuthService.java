package com.barapp.service;

import com.barapp.dto.LoginRequest;
import com.barapp.dto.LoginResponse;
import com.barapp.dto.RegisterRequest;
import com.barapp.dto.UserResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    UserResponse register(RegisterRequest request);
    UserResponse getByEmail(String email);
}
