package com.ls.lsback.service.impl;

import com.ls.lsback.entity.CategorieEntity;
import com.ls.lsback.entity.MemoEntity;
import com.ls.lsback.repository.CategorieRepository;
import com.ls.lsback.repository.MemoRepository;
import com.ls.lsback.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    @Autowired
    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public List<MemoEntity> listMemo() {
        return memoRepository.findAll();
    }

}
