package com.barapp.service;

import com.barapp.dto.*;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    RegisterResponse register(RegisterRequest request);
}
