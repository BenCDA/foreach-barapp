package com.barapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class IngredientResponse {
    private Long id;
    private String name;
}
