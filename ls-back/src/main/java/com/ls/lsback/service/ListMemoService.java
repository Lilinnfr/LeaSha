package com.ls.lsback.service;

import com.ls.lsback.entity.ListMemoEntity;

import java.util.List;

public interface ListMemoService {

    List<ListMemoEntity> listMemoListe();

    ListMemoEntity getMemoListe(long id);

    ListMemoEntity addMemoListe(ListMemoEntity listMemoEntity);

    boolean deleteMemoListe(long id);
}
