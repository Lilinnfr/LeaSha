package com.ls.lsback.model;

public enum CategorieMemo {

    LANGUES(1L, "Langues"),
    FRANCAIS(2L, "Français"),
    MATHEMATIQUES(3L, "Mathématiques"),
    CUISINE(4L, "Cuisine"),
    AUTRES(5L, "Autres");

    private final Long id;
    private final String libelle;

    private CategorieMemo(Long id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    // Méthode pour rechercher une instance de l'énumération par son identifiant
    public static CategorieMemo getById(Long id) {
        for (CategorieMemo categorie : CategorieMemo.values()) {
            if (categorie.getId().equals(id)) {
                return categorie;
            }
        }
        throw new IllegalArgumentException("Aucune catégorie trouvée");
    }

}
