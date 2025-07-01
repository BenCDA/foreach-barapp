// src/main/java/com/barapp/model/OrderCocktail.java
package com.barapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "cocktail_commande")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderCocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // id_commande INTEGER REFERENCES commande(id)
    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    private Order order;

    // id_cocktail INTEGER REFERENCES cocktail(id)
    @ManyToOne
    @JoinColumn(name = "id_cocktail", nullable = false)
    private Cocktail cocktail;

    // id_taille INTEGER REFERENCES taille(id)
    @ManyToOne
    @JoinColumn(name = "id_taille", nullable = false)
    private Size size;

    // statut VARCHAR(30) CHECK (statut IN ('PREPARATION','ASSEMBLAGE','DRESSAGE','TERMINE'))
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false, length = 30)
    private Step step;

    public enum Step {
        PREPARATION,
        ASSEMBLAGE,
        DRESSAGE,
        TERMINE
    }
}
