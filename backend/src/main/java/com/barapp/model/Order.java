package com.barapp.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "commande")            // ← impératif !
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Order {

    public enum Status {
        COMMANDEE,
        EN_PREPARATION,
        TERMINEE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private User user;

    @Column(name = "date_commande")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Status status;
}
