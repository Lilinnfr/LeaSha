package com.ls.lsback.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.ListMemoEntity;
import com.ls.lsback.repository.ListMemoRepository;
import com.ls.lsback.service.ListMemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListMemoServiceImpl implements ListMemoService {

    private final ListMemoRepository listMemoRepository;

    @Autowired
    public ListMemoServiceImpl(ListMemoRepository listMemoRepository, ObjectMapper objectMapper) {
        this.listMemoRepository = listMemoRepository;
    }

    public List<ListMemoEntity> listMemoListe() {
        return listMemoRepository.findAll();
    }

    @Override
    public ListMemoEntity getMemoListe(long id) {
        return listMemoRepository.findById(id).orElse(null);
    }

    @Override
    public ListMemoEntity addMemoListe(ListMemoEntity listMemoEntity) {
        return listMemoRepository.save(listMemoEntity);
    }

    @Override
    public boolean deleteMemoListe(long id) {
        Optional<ListMemoEntity> memo = listMemoRepository.findById(id);
        if (memo.isPresent()) {
            listMemoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
