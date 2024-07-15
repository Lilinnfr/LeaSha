package com.ls.lsback.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.CardMemoEntity;
import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.repository.CardMemoRepository;
import com.ls.lsback.service.CardMemoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardMemoServiceImpl implements CardMemoService {

    private final CardMemoRepository cardMemoRepository;

    @Autowired
    public CardMemoServiceImpl(CardMemoRepository cardMemoRepository, ObjectMapper objectMapper) {
        this.cardMemoRepository = cardMemoRepository;
    }

    @Override
    public List<CardMemoEntity> listMemoCarte(String email) {
        return cardMemoRepository.findByUtilisateurEmail(email);
    }

    @Override
    public CardMemoEntity getMemoCarte(long id) {
        return cardMemoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Mémo non trouvé avec ID " + id));
    }

    @Override
    public CardMemoEntity addMemoCarte(CardMemoEntity cardMemo) {
        UtilisateurEntity utilisateur = (UtilisateurEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cardMemo.setUtilisateur(utilisateur);
        return cardMemoRepository.save(cardMemo);
    }

    @Override
    public CardMemoEntity updateMemoCarte(long id, CardMemoEntity cardMemoEntity) {
        CardMemoEntity memoToUpdate = cardMemoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pas de mémo avec id " + id));
        memoToUpdate.setTitre(cardMemoEntity.getTitre());
        memoToUpdate.setCategorie(cardMemoEntity.getCategorie());
        memoToUpdate.setDescription(cardMemoEntity.getDescription());
        return cardMemoRepository.save(memoToUpdate);
    }

    @Override
    public void deleteMemoCarte(long id) {
        CardMemoEntity memo = cardMemoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Le mémo avec l'id " + id + " n'existe pas."));
        cardMemoRepository.delete(memo);
    }
}
