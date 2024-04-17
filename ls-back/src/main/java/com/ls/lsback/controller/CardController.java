package com.ls.lsback.controller;

import com.ls.lsback.entity.CardEntity;
import com.ls.lsback.service.CardService;
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

    @GetMapping("/liste")
    public ResponseEntity<List<CardEntity>> listCard() {
        List<CardEntity> cartes = cardService.listCarte();
        if (cartes.isEmpty()) {
            log.info("Cartes non dispo");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cartes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardEntity> getCardById(@PathVariable("id") long id) {
        CardEntity card = cardService.getCarte(id);
        if (card == null) {
            log.info("Mémo avec ID {} non trouvé", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @PostMapping("/creation")
    public ResponseEntity<CardEntity> createCard(@RequestBody CardEntity cardEntity) {
        CardEntity createdCard = cardService.addCarte(cardEntity);
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
