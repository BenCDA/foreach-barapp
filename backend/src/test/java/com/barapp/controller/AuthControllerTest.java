package com.barapp.controller;

import com.barapp.dto.LoginRequest;
import com.barapp.dto.RegisterRequest;
import com.barapp.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("POST /api/auth/login → 200 + {token}")
    void loginSuccess() throws Exception {
        // Arrange
        String jwt = "mock-jwt-token";
        when(authService.login(any(LoginRequest.class))).thenReturn(jwt);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user@example.com\",\"password\":\"Pass123!\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.token").value(jwt));

        verify(authService).login(argThat(req ->
            "user@example.com".equals(req.getEmail())
        ));
    }

    @Test
    @DisplayName("POST /api/auth/register → 201 CREATED")
    void registerSuccess() throws Exception {
        // Arrange
        doNothing().when(authService).register(any(RegisterRequest.class));

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"
                    + "\"name\":\"Jean Dupont\","
                    + "\"email\":\"jean.dupont@mail.com\","
                    + "\"password\":\"Pass123!\","
                    + "\"role\":\"USER\""
                    + "}"))
            .andExpect(status().isCreated());

        verify(authService).register(argThat(req ->
            "jean.dupont@mail.com".equals(req.getEmail()) &&
            "Pass123!".equals(req.getPassword()) &&
            "USER".equals(req.getRole().name())
        ));
    }

    @Test
    @DisplayName("POST /api/auth/login with bad credentials → 401 UNAUTHORIZED")
    void loginBadCredentials() throws Exception {
        // Arrange
        when(authService.login(any(LoginRequest.class)))
            .thenThrow(new BadCredentialsException("Bad creds"));

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user@example.com\",\"password\":\"wrong\"}"))
            .andExpect(status().isUnauthorized());
    }
}
