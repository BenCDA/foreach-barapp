// src/main/java/com/barapp/service/AuthService.java
package com.barapp.service;

import com.barapp.dto.LoginRequest;
import com.barapp.dto.RegisterRequest;
import com.barapp.dto.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);
    String        login(LoginRequest   request);
}
