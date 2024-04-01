package com.ls.lsback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorie")
@Getter
@Setter
@NoArgsConstructor
public class CategorieEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "nom", nullable = false)
    private String nom;
}
