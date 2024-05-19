package com.ls.lsback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "validation")
@Getter
@Setter
@NoArgsConstructor
public class ValidationEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // date de création
    private Instant creation;
    // durée de validité du code
    private Instant expiration;
    // le moment où le compte est activé
    private Instant activation;
    // le code en question
    private String code;
    // l'utilisateur
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH})
    private UtilisateurEntity utilisateur;

}
