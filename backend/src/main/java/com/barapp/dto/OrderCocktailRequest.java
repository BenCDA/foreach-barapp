package com.barapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Requête de création d'une ligne de commande de cocktail.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderCocktailRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private Long cocktailId;

    @NotNull
    private Long sizeId;


    @NotNull
    private com.barapp.model.OrderCocktail.Step step;

}
