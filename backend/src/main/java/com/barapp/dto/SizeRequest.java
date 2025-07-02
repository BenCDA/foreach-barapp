package com.barapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SizeRequest {
    @NotBlank
    @Pattern(regexp = "S|M|L", message = "Le label doit être S, M ou L")
    private String label;
}
