package com.barapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CocktailIngredientRequest {
    @NotNull
    private Long cocktailId;

    @NotNull
    private Long ingredientId;

    @NotBlank
    private String quantity; // ex: 5cl
}
