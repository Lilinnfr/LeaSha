package com.ls.lsback.model;

import com.fasterxml.jackson.annotation.JsonCreator; // pour éviter l'erreur de sérialisation
import com.fasterxml.jackson.annotation.JsonValue;

public enum CategorieMemo {

    LANGUES("Langues"),
    FRANCAIS("Français"),
    MATHEMATIQUES("Mathématiques"),
    CUISINE("Cuisine"),
    AUTRES("Autres");

    private final String libelle;

    private CategorieMemo(String libelle) {
        this.libelle = libelle;
    }

    @JsonValue
    public String getLibelle() {
        return libelle;
    }

    // Méthode pour rechercher une instance de l'énumération par son libellé
    @JsonCreator
    public static CategorieMemo valueOfLibelle(String libelle) {
        for (CategorieMemo categorie : CategorieMemo.values()) {
            if (categorie.getLibelle().equalsIgnoreCase(libelle)) {
                return categorie;
            }
        }
        throw new IllegalArgumentException("Aucune catégorie trouvée");
    }

}
