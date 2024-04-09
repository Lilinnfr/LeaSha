package com.ls.lsback.service.impl;

import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.repository.UtilisateurRepository;
import com.ls.lsback.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<UtilisateurEntity> listUtilisateur() {
        return utilisateurRepository.findAll();
    }
}
