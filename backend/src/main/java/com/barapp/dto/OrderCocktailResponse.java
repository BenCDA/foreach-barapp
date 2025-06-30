package com.barapp.dto;

import com.barapp.model.OrderCocktail.Step;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class OrderCocktailResponse {
    private Long id;
    private Long orderId;
    private Long cocktailId;
    private Long sizeId;
    private Step step;
}
