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
    public CardEntity addCarte(CardEntity cardEntity) {
        return cardRepository.save(cardEntity);
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
