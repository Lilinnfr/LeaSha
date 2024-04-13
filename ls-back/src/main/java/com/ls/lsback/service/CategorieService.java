package com.ls.lsback.service;

import com.ls.lsback.entity.CategorieEntity;

import java.util.List;

public interface CategorieService {

    List<CategorieEntity> listCategorie();

    CategorieEntity getCategorie(long id);
}
