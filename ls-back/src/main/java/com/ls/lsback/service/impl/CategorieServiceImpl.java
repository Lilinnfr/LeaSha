package com.ls.lsback.service.impl;

import com.ls.lsback.entity.CategorieEntity;
import com.ls.lsback.repository.CategorieRepository;
import com.ls.lsback.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<CategorieEntity> listCategorie() {
        return categorieRepository.findAll();
    }

    @Override
    public CategorieEntity getCategorie(long id) {
        return categorieRepository.findById(id).orElse(null);
    }

}
