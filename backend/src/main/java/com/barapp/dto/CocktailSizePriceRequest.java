package com.barapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CocktailSizePriceRequest {
    @NotNull
    private Long cocktailId;

    @NotNull
    private Long sizeId;

    @NotNull
    private Integer price;
}
