package com.ls.lsback.service;

import com.ls.lsback.entity.ListMemoEntity;

import java.util.List;

public interface ListMemoService {

    List<ListMemoEntity> listMemoListe(String email);

    ListMemoEntity getMemoListe(long id);

    ListMemoEntity save(ListMemoEntity listMemoEntity);

    ListMemoEntity addMemoListe(ListMemoEntity listMemoEntity);

    ListMemoEntity updateMemoListe(long id, ListMemoEntity listMemoEntity);

    void deleteMemoListe(long id);
}
