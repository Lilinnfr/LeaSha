package com.ls.lsback.service;

import com.ls.lsback.entity.UtilisateurEntity;

import java.util.List;
import java.util.Map;

public interface UtilisateurService {

    List<UtilisateurEntity> listUtilisateur();
    UtilisateurEntity addUtilisateur(UtilisateurEntity utilisateurEntity);
    public void deleteUtilisateur(long id);
    void activation(Map<String, String> activation);
}
