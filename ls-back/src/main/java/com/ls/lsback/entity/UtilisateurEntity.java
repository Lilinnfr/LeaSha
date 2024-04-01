package com.ls.lsback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "utilisateur")
@Getter
@Setter
@NoArgsConstructor
public class UtilisateurEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "identifiant", nullable = false)
    private String identifiant;

    @Column(name = "courriel", nullable = false)
    private String courriel;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(name = "date_creation", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreation;

    @Column(name = "date_modif", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateModif;

    @Column(name = "infos", columnDefinition = "JSONB")
    private String infos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memo_id", referencedColumnName = "id")
    private MemoEntity memo;

}
