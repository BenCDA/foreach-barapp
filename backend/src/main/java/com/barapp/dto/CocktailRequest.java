package com.barapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CocktailRequest {
    @NotBlank
    private String name;

    private String description;

    private String imageUrl;

    @NotNull
    private Long categoryId;
}
