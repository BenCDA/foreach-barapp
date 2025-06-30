package com.barapp.dto;

import com.barapp.model.OrderCocktail.Step;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderCocktailRequest {
    @NotNull
    private Long orderId;

    @NotNull
    private Long cocktailId;

    @NotNull
    private Long sizeId;

    private Step step = Step.PREPARATION;
}
