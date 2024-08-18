package com.ls.lsback.controller;

import com.ls.lsback.entity.CardMemoEntity;
import com.ls.lsback.entity.ListMemoEntity;
import com.ls.lsback.service.ListMemoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins={"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping(value="/memoListe")
@Slf4j
public class ListMemoController {

    private final ListMemoService listMemoService;

    public ListMemoController(ListMemoService listMemoService) {
        this.listMemoService = listMemoService;
    }

    @GetMapping("/Mes mémos listes")
    public ResponseEntity<List<ListMemoEntity>> listMemoList() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = userDetails.getUsername();
            log.info("Récupération des mémos");
            List<ListMemoEntity> memos = listMemoService.listMemoListe(email);
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
    public ResponseEntity<ListMemoEntity> getListMemoById(@PathVariable("id") long id) {
        try {
            ListMemoEntity memo = listMemoService.getMemoListe(id);
            return new ResponseEntity<>(memo, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.info("Mémo avec id {} non trouvé", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/creation")
    public ResponseEntity<ListMemoEntity> createListMemo(@RequestBody ListMemoEntity listMemoEntity) {
        try {
            ListMemoEntity createdListMemo = listMemoService.addMemoListe(listMemoEntity);
            return new ResponseEntity<>(createdListMemo, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Erreur lors de la création du mémo", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/modification/{id}")
    public ResponseEntity<ListMemoEntity> updateListMemo(@PathVariable("id") long id, @RequestBody ListMemoEntity updatedMemo) {
        try {
            ListMemoEntity existingListMemo = listMemoService.getMemoListe(id);

            existingListMemo.setTitre(updatedMemo.getTitre());
            existingListMemo.setCategorie(updatedMemo.getCategorie());
            existingListMemo.setDescription(updatedMemo.getDescription());
            existingListMemo.setListe(updatedMemo.getListe());

            ListMemoEntity updatedListMemo = listMemoService.save(existingListMemo);
            return new ResponseEntity<>(updatedListMemo, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            log.info("Pas de mémo avec id {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour du mémo", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/suppression/{id}")
    public ResponseEntity<Void> deleteListMemoById(@PathVariable long id) {
        try {
            listMemoService.deleteMemoListe(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la suppression du mémo avec l'id : {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
