package com.ls.lsback.service.impl;

import com.ls.lsback.entity.CardEntity;
import com.ls.lsback.entity.MemoCardEntity;
import com.ls.lsback.repository.CardRepository;
import com.ls.lsback.repository.MemoCardRepository;
import com.ls.lsback.service.CardService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl  implements CardService {

    private final CardRepository cardRepository;

    private final MemoCardRepository memoCardRepository;


    @Autowired
    public CardServiceImpl(CardRepository cardRepository, MemoCardRepository memoCardRepository) {
        this.cardRepository = cardRepository;
        this.memoCardRepository = memoCardRepository;
    }

    @Override
    public List<CardEntity> listCartesByMemo(long memoId) {
        MemoCardEntity memo = memoCardRepository.findById(memoId)
                .orElseThrow(() -> new EntityNotFoundException("Mémo non trouvé avec ID " + memoId));
        return cardRepository.findAllCardsByMemoId(memo);
    }

    @Override
    public CardEntity getCarte(long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carte non trouvée avec ID " + id));
    }

    @Override
    public CardEntity addCarte(CardEntity cardEntity, long memoId) {
        MemoCardEntity memo = memoCardRepository.findById(memoId)
                .orElseThrow(() -> new EntityNotFoundException("Mémo non trouvé avec ID " + memoId));
        cardEntity.setMemoId(memo);
        return cardRepository.save(cardEntity);
    }

    @Override
    public CardEntity updateCarte(long id, CardEntity cardEntity) {
        CardEntity existingCard = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carte non trouvée avec ID " + id));
        existingCard.setRecto(cardEntity.getRecto());
        existingCard.setVerso(cardEntity.getVerso());
        return cardRepository.save(existingCard);
    }

    @Override
    public boolean deleteCarte(long id) {
        cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carte non trouvée avec ID " + id));
        cardRepository.deleteById(id);
        return true;
    }
}
