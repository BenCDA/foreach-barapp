package com.barapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "panier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_utilisateur")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cocktail")
    private Cocktail cocktail;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_taille")
    private Size size;
}
