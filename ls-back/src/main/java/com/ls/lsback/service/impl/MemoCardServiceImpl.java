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

    @Override
    public List<MemoCardEntity> listMemoCarte(String email) {
        return memoCardRepository.findByUtilisateurEmail(email);
    }

    @Override
    public MemoCardEntity getMemoCarte(long id) {
        return memoCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mémo non trouvé avec ID " + id));
    }

    @Override
    public MemoCardEntity addMemoCarte(MemoCardEntity memoCard) {
        UtilisateurEntity utilisateur = (UtilisateurEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memoCard.setUtilisateur(utilisateur);
        return memoCardRepository.save(memoCard);
    }

    @Override
    public MemoCardEntity updateMemoCarte(long id, MemoCardEntity memoCardEntity) {
        MemoCardEntity memoToUpdate = memoCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pas de mémo avec id " + id));
        memoToUpdate.setTitre(memoCardEntity.getTitre());
        memoToUpdate.setCategorie(memoCardEntity.getCategorie());
        memoToUpdate.setDescription(memoCardEntity.getDescription());
        return memoCardRepository.save(memoToUpdate);
    }

    @Override
    public void deleteMemoCarte(long id) {
        MemoCardEntity memo = memoCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Le mémo avec l'id " + id + " n'existe pas."));
        memoCardRepository.delete(memo);
    }
}
