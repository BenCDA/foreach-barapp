package com.barapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CocktailIngredientResponse {
    private Long id;
    private Long cocktailId;
    private Long ingredientId;
    private String quantity;
}
