package com.ls.lsback.service;

import com.ls.lsback.entity.UtilisateurEntity;

import java.util.List;

public interface UtilisateurService {

    List<UtilisateurEntity> listUtilisateur();

    UtilisateurEntity addUtilisateur(UtilisateurEntity utilisateurEntity);

    public void deleteUtilisateur(long id);

}
