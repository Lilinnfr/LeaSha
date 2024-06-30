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

    public List<CardEntity> listCartesByMemo(long memoId) {
        Optional<MemoCardEntity> memoById = memoCardRepository.findById(memoId);
        if (memoById.isPresent()) {
            MemoCardEntity memo = memoById.get();
            List<CardEntity> cartes = cardRepository.findAllCardsByMemoId(memo);
            return cartes;
        } else {
            throw new EntityNotFoundException("Mémo non trouvé");
        }
    }

    @Override
    public CardEntity getCarte(long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public CardEntity addCarte(CardEntity cardEntity, long memoId) {
        // on récupère l'id du mémo
        Optional<MemoCardEntity> memo = memoCardRepository.findById(memoId);
        if (memo.isPresent()) {
            // on associe le mémo à la carte
            cardEntity.setMemoId(memo.get());
            // on sauvegarde la carte avec le mémo associé
            return cardRepository.save(cardEntity);
        } else {
            throw new EntityNotFoundException("Pas de mémo trouvé avec l'id " + memoId);
        }
    }

    @Override
    public CardEntity updateCarte(long id, CardEntity cardEntity) {
        // On vérifie si la carte existe
        Optional<CardEntity> existingCard = cardRepository.findById(id);
        if (existingCard.isPresent()) {
            CardEntity cardToUpdate = existingCard.get();
            // On met à jour les champs de la carte
            cardToUpdate.setRecto(cardEntity.getRecto());
            cardToUpdate.setVerso(cardEntity.getVerso());
            // On sauvegarde la carte mise à jour
            return cardRepository.save(cardToUpdate);
        } else {
            throw new EntityNotFoundException("Pas de carte avec ID " + id);
        }
    }

    @Override
    public boolean deleteCarte(long id) {
        Optional<CardEntity> card = cardRepository.findById(id);
        if (card.isPresent()) {
            cardRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
