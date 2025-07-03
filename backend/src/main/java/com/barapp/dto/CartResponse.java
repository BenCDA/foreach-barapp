package com.barapp.dto;

import lombok.Data;

@Data
public class CartResponse {
    private Long id;
    private String cocktailName;
    private String sizeLabel;
    private Integer price; // Prix affiché dans le panier (par taille/cocktail)
}
