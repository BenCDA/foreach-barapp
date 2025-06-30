package com.barapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CocktailResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Long categoryId;
}
