package com.ls.lsback.service;

import com.ls.lsback.entity.CardMemoEntity;

import java.util.List;

public interface CardMemoService {

    List<CardMemoEntity> listMemoCarte(String email);

    CardMemoEntity getMemoCarte(long id);

    CardMemoEntity addMemoCarte(CardMemoEntity cardMemoEntity);

    CardMemoEntity updateMemoCarte(long id, CardMemoEntity cardMemoEntity);

    void deleteMemoCarte(long id);
}
