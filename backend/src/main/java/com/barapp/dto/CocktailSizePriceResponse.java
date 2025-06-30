package com.barapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CocktailSizePriceResponse {
    private Long id;
    private Long cocktailId;
    private Long sizeId;
    private Integer price;
}
