package com.barapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CocktailRequest {
    private String name;
    private String description;
    private String imageUrl;
    private Long categoryId;
}
