package com.ls.lsback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ls.lsback.model.CategorieMemo;
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
public class ListMemoEntity {

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

    @Column(name = "categorie", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategorieMemo categorie;

}
