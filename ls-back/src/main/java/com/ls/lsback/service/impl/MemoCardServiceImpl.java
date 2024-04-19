package com.ls.lsback.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.MemoCardEntity;
import com.ls.lsback.repository.MemoCardRepository;
import com.ls.lsback.service.MemoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemoCardServiceImpl implements MemoCardService {

    private final MemoCardRepository memoCardRepository;

    @Autowired
    public MemoCardServiceImpl(MemoCardRepository memoCardRepository, ObjectMapper objectMapper) {
        this.memoCardRepository = memoCardRepository;
    }

    public List<MemoCardEntity> listMemoCarte() {
        return memoCardRepository.findAll();
    }

    @Override
    public MemoCardEntity getMemoCarte(long id) {
        return memoCardRepository.findById(id).orElse(null);
    }

    @Override
    public MemoCardEntity addMemoCarte(MemoCardEntity memoCardEntity) {
        return memoCardRepository.save(memoCardEntity);
    }

    @Override
    public boolean deleteMemoCarte(long id) {
        Optional<MemoCardEntity> memo = memoCardRepository.findById(id);
        if (memo.isPresent()) {
            memoCardRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}