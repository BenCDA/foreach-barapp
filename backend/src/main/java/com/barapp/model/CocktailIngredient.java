// src/main/java/com/barapp/model/CocktailIngredient.java
package com.barapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cocktail_ingredient")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CocktailIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // id_cocktail INTEGER REFERENCES cocktail(id)
    @ManyToOne
    @JoinColumn(name = "id_cocktail", nullable = false)
    private Cocktail cocktail;

    // id_ingredient INTEGER REFERENCES ingredient(id)
    @ManyToOne
    @JoinColumn(name = "id_ingredient", nullable = false)
    private Ingredient ingredient;

    // quantite VARCHAR(50)
    @Column(name = "quantite", nullable = false, length = 50)
    private String quantity;
}
