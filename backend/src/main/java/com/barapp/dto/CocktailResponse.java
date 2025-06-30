package com.barapp.dto;

import com.barapp.model.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CocktailResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Category category;  // catégorie complète
}
