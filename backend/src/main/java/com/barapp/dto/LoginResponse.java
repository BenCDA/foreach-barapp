package com.barapp.dto;

import com.barapp.model.User.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
