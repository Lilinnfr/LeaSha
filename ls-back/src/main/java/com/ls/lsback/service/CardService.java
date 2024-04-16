package com.ls.lsback.service;

import com.ls.lsback.entity.CardEntity;

import java.util.List;

public interface CardService {

    List<CardEntity> listCarte();

    CardEntity getCarte(long id);

    CardEntity addCarte(CardEntity cardEntity);

    boolean deleteCarte(long id);

}
