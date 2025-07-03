package com.barapp.controller;

import com.barapp.dto.UserRequest;
import com.barapp.dto.UserResponse;
import com.barapp.model.User;
import com.barapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.barapp.model.User.Role.CLIENT;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void create_shouldReturnCreated() throws Exception {
        Mockito.when(userService.createUser(any(UserRequest.class)))
                .thenReturn(new UserResponse(1L, "Ben", "ben@test.com", CLIENT));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Ben\",\"email\":\"ben@test.com\",\"password\":\"pwd\",\"role\":\"CLIENT\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("ben@test.com"));
    }

    @Test
    void getAll_shouldReturnOk() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }
}
