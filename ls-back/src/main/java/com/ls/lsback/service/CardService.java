package com.ls.lsback.service;

import com.ls.lsback.entity.CardEntity;

import java.util.List;

public interface CardService {

    List<CardEntity> listCartesByMemo(long memoId);

    CardEntity getCarte(long id);

    CardEntity addCarte(CardEntity cardEntity, long memoId);

    boolean deleteCarte(long id);

}
