package com.ls.lsback.entity;

import com.ls.lsback.model.TypeMemo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "memo")
@Getter
@Setter
@NoArgsConstructor
public class MemoEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "contenu", columnDefinition = "json")
    private String contenu;

    @Column(name = "date_creation", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreation;

    @Column(name = "date_modif", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateModif;

    @Column(name = "type_memo", length = 50)
    @Enumerated(EnumType.STRING)
    private TypeMemo typeMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id")
    private CategorieEntity categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creer_par")
    private UtilisateurEntity creerPar;
}
