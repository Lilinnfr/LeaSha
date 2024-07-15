package com.ls.lsback.controller;

import com.ls.lsback.entity.CardMemoEntity;
import com.ls.lsback.service.CardMemoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins={"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping(value="/memoCarte")
@Slf4j
public class CardMemoController {

    private final CardMemoService cardMemoService;

    public CardMemoController(CardMemoService cardMemoService) {
        this.cardMemoService = cardMemoService;
    }

    @GetMapping("/Mes mémos cartes")
    public ResponseEntity<List<CardMemoEntity>> listMemoCard() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = userDetails.getUsername();
            log.info("Récupération des mémos");
            List<CardMemoEntity> memos = cardMemoService.listMemoCarte(email);
            if (memos.isEmpty()) {
                log.info("Aucun mémo trouvé");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(memos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des mémos", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardMemoEntity> getMemoCardById(@PathVariable("id") long id) {
        try {
            CardMemoEntity memo = cardMemoService.getMemoCarte(id);
            return new ResponseEntity<>(memo, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.info("Mémo avec id {} non trouvé", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/creation")
    public ResponseEntity<CardMemoEntity> createMemoCard(@RequestBody CardMemoEntity cardMemoEntity) {
        try {
            CardMemoEntity createdMemoCard = cardMemoService.addMemoCarte(cardMemoEntity);
            return new ResponseEntity<>(createdMemoCard, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Erreur lors de la création du mémo", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/modification/{id}")
    public ResponseEntity<CardMemoEntity> updateMemoCard(@PathVariable("id") long id, @RequestBody CardMemoEntity cardMemoEntity) {
        try {
            CardMemoEntity updatedMemoCard = cardMemoService.updateMemoCarte(id, cardMemoEntity);
            return new ResponseEntity<>(updatedMemoCard, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.info("Pas de mémo avec id {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/suppression/{id}")
    public ResponseEntity<Void> deleteMemoCardById(@PathVariable long id) {
        try {
            cardMemoService.deleteMemoCarte(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la suppression du mémo avec l'id : {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
