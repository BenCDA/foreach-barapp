package com.barapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CocktailIngredientRequest {
    private Long cocktailId;
    private Long ingredientId;
    private String quantity;
}
