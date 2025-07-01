package com.barapp.dto;

import com.barapp.model.User.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {
    @NotBlank
    private String name;

    @Email @NotBlank
    private String email;
 @NotBlank
    @Size(min = 8, message = "Le mot de passe doit faire au moins 8 caractères")
    @Pattern(
      regexp = "^(?=.{8,}$)(?=.*[0-9\\W]).+$",
      message = "Le mot de passe doit contenir au moins un chiffre ou un caractère spécial"
    )
    @NotBlank
    private String password;

    private Role role;
}
