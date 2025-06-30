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
@Table(name = "cocktail_taille_prix")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CocktailSizePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cocktail")
    private Cocktail cocktail;

    @ManyToOne
    @JoinColumn(name = "id_taille")
    private Size size;

    @Column(name = "prix")
    private Integer price;
}
