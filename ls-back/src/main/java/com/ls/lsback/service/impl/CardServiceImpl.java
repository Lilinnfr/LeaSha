package com.ls.lsback.service.impl;

import com.ls.lsback.entity.CardEntity;
import com.ls.lsback.repository.CardRepository;
import com.ls.lsback.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl  implements CardService {

    private final CardRepository cardRepository;


    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<CardEntity> listCarte() {
        return cardRepository.findAll();
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
