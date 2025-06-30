package com.barapp.service;

import com.barapp.dto.UserRequest;
import com.barapp.dto.UserResponse;
import com.barapp.model.User;
import com.barapp.repository.UserRepository;
import com.barapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.List;

import static com.barapp.model.User.Role.CLIENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void createUser_shouldReturnUserResponse_whenValidRequest() {
        // Arrange
        UserRequest request = new UserRequest();
        request.setName("Benjamin");
        request.setEmail("benjamin@test.com");
        request.setPassword("123456");
        request.setRole(CLIENT);

        when(userRepository.existsByEmail("benjamin@test.com")).thenReturn(false);

        User savedUser = new User(1L, "Benjamin", "benjamin@test.com", "123456", CLIENT);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponse response = userService.createUser(request);

        // Assert
        assertNotNull(response);
        assertEquals("Benjamin", response.getName());
        assertEquals("benjamin@test.com", response.getEmail());
        assertEquals(CLIENT, response.getRole());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAllUsers_shouldReturnEmptyList_whenNoUsers() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponse> users = userService.getAllUsers();

        assertTrue(users.isEmpty());
    }
}
