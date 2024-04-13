package com.ls.lsback.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.MemoListEntity;
import com.ls.lsback.repository.MemoListRepository;
import com.ls.lsback.service.MemoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemoListServiceImpl implements MemoListService {

    private final MemoListRepository memoListRepository;

    @Autowired
    public MemoListServiceImpl(MemoListRepository memoListRepository, ObjectMapper objectMapper) {
        this.memoListRepository = memoListRepository;
    }

    public List<MemoListEntity> listMemoListe() {
        return memoListRepository.findAll();
    }

    @Override
    public MemoListEntity getMemoListe(long id) {
        return memoListRepository.findById(id).orElse(null);
    }

    @Override
    public MemoListEntity addMemoListe(MemoListEntity memoListEntity) {
        return memoListRepository.save(memoListEntity);
    }

    @Override
    public boolean deleteMemoListe(long id) {
        Optional<MemoListEntity> memo = memoListRepository.findById(id);
        if (memo.isPresent()) {
            memoListRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
