// src/main/java/com/barapp/dto/OrderCocktailRequest.java
package com.barapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class OrderCocktailRequest {
    @NotNull
    private com.barapp.model.OrderCocktail.Step step;
}
