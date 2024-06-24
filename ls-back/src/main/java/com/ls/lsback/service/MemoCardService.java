package com.ls.lsback.service;

import com.ls.lsback.entity.MemoCardEntity;

import java.util.List;

public interface MemoCardService {

    List<MemoCardEntity> listMemoCarte(String email);

    MemoCardEntity getMemoCarte(long id);

    public void addMemoCarte(MemoCardEntity memoCardEntity);

    public void deleteMemoCarte(long id);

}
