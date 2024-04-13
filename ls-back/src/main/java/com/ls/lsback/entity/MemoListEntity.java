package com.ls.lsback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "memo_liste")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MemoListEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "description")
    private String description;

    @Column(name = "liste", length = 3000)
    private String liste;

    @Column(name = "date_creation", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateCreation;

    @Column(name = "date_modif", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateModif;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id", referencedColumnName = "id")
    private CategorieEntity categorieId;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creer_par")
    private UtilisateurEntity creerPar;*/
}
