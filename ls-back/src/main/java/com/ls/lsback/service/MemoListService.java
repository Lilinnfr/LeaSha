package com.ls.lsback.service;

import com.ls.lsback.entity.MemoListEntity;

import java.util.List;

public interface MemoListService {

    List<MemoListEntity> listMemoListe();

    MemoListEntity getMemoListe(long id);

    MemoListEntity addMemoListe(MemoListEntity memoListEntity);

    boolean deleteMemoListe(long id);
}
