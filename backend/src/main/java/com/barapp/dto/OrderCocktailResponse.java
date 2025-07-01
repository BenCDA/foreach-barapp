package com.barapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Réponse DTO pour une ligne de commande de cocktail.
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderCocktailResponse {

    private Long id;
    private Long orderId;
    private Long cocktailId;
    private Integer quantity;
    private com.barapp.model.OrderCocktail.Step step;

    public static OrderCocktailResponse fromEntity(com.barapp.model.OrderCocktail oc) {
        OrderCocktailResponse dto = new OrderCocktailResponse();
        dto.setId(oc.getId());
        dto.setOrderId(oc.getOrder().getId());
        dto.setCocktailId(oc.getCocktail().getId());
        dto.setQuantity(oc.getQuantity());
        dto.setStep(oc.getStep());
        return dto;
    }
}
