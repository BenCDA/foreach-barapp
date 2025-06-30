package com.barapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CocktailIngredientResponse {
    private Long id;
    private Long cocktailId;
    private Long ingredientId;
    private String quantity;
}
