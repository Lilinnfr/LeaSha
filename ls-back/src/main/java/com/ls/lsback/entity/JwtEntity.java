package com.ls.lsback.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jwt")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valeur;
    private boolean desactive;
    private boolean expire;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "utilisateur_id")
    private UtilisateurEntity utilisateur;

}
