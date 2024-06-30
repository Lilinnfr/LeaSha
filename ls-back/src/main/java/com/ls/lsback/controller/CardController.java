package com.ls.lsback.controller;

import com.ls.lsback.entity.CardEntity;
import com.ls.lsback.entity.MemoCardEntity;
import com.ls.lsback.repository.CardRepository;
import com.ls.lsback.repository.MemoCardRepository;
import com.ls.lsback.service.CardService;
import com.ls.lsback.service.MemoCardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins={"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping(value="/memoCarte/carte")
@Slf4j
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;

    }

    @GetMapping("/{memoId}")
    public ResponseEntity<List<CardEntity>> listCardsByMemoId(@PathVariable("memoId") long memoId) {
        try {
            List<CardEntity> cartes = cardService.listCartesByMemo(memoId);
            return new ResponseEntity<>(cartes, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.info("Mémo non dispo");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{memoId}/{carteId}")
    public ResponseEntity<CardEntity> getCardById(@PathVariable("memoId") long memoId, @PathVariable("carteId") long id) {
        CardEntity card = cardService.getCarte(id);
        if (card == null) {
            log.info("Carte avec ID {} non trouvé", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PostMapping("/creation")
    public ResponseEntity<CardEntity> createCard(@RequestBody CardEntity cardEntity, @RequestParam Long memoId) {
        CardEntity createdCard = cardService.addCarte(cardEntity, memoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
    }

    @DeleteMapping("/suppression/{id}")
    public ResponseEntity<Void> deleteCardById(@PathVariable long id) {
        boolean deletedCard = cardService.deleteCarte(id);
        if (deletedCard) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
