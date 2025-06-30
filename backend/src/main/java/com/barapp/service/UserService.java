package com.barapp.service;

import java.util.List;

import com.barapp.dto.UserRequest;
import com.barapp.dto.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    void deleteUser(Long id);
}
