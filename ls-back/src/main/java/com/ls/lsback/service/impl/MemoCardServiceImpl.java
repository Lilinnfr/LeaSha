package com.ls.lsback.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.MemoCardEntity;
import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.repository.MemoCardRepository;
import com.ls.lsback.service.MemoCardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public List<MemoCardEntity> listMemoCarte(String email) {
        List<MemoCardEntity> memos = memoCardRepository.findByUtilisateurEmail(email);
        return memos;
    }

    @Override
    public MemoCardEntity getMemoCarte(long id) {
        return memoCardRepository.findById(id).orElse(null);
    }

    public MemoCardEntity addMemoCarte(MemoCardEntity memoCard) {
        UtilisateurEntity utilisateur = (UtilisateurEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memoCard.setUtilisateur(utilisateur);
        return this.memoCardRepository.save(memoCard);
    }

    @Override
    public MemoCardEntity updateMemoCarte(long id, MemoCardEntity memoCardEntity) {
        Optional<MemoCardEntity> existingMemo = memoCardRepository.findById(id);
        if (existingMemo.isPresent()) {
            MemoCardEntity memoToUpdate = existingMemo.get();
            memoToUpdate.setTitre(memoCardEntity.getTitre());
            memoToUpdate.setCategorie(memoCardEntity.getCategorie());
            memoToUpdate.setDescription(memoCardEntity.getDescription());
            return this.memoCardRepository.save(memoToUpdate);
        } else {
            throw new EntityNotFoundException("Pas de mémo avec id " + id);
        }
    }

    @Override
    public void deleteMemoCarte(long id) {
        Optional<MemoCardEntity> memo = memoCardRepository.findById(id);
        if (memo.isPresent()) {
            memoCardRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Le mémo avec l'ID " + id + " n'existe pas.");
        }
    }
}
