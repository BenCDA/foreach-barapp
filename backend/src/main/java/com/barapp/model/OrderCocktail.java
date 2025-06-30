package com.barapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderCocktail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Cocktail cocktail;

    @ManyToOne
    private Size size;

    @Enumerated(EnumType.STRING)
    private Step step;

    public enum Step {
        PREPARATION,
        ASSEMBLY,
        DRESSING,
        DONE
    }
}
