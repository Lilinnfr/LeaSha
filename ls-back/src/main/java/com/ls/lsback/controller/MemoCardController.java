package com.ls.lsback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.MemoCardEntity;
import com.ls.lsback.service.MemoCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins={"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping(value="/memoCarte")
@Slf4j
public class MemoCardController {

    private final MemoCardService memoCardService;
    private final ObjectMapper objectMapper;

    public MemoCardController(MemoCardService memoCardService, ObjectMapper objectMapper) {
        this.memoCardService = memoCardService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/Mes mémos cartes")
    public ResponseEntity<List<MemoCardEntity>> listMemoCard() {
        List<MemoCardEntity> memos = memoCardService.listMemoCarte();
        if (memos.isEmpty()) {
            log.info("Mémos non dispo");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(memos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoCardEntity> getMemoCardById(@PathVariable("id") long id) {
        MemoCardEntity memo = memoCardService.getMemoCarte(id);
        if (memo == null) {
            log.info("Mémo avec ID {} non trouvé", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(memo, HttpStatus.OK);
    }

/*    @PostMapping("/creation")
    public ResponseEntity<MemoCardEntity> createMemoCard(@RequestBody MemoCardEntity memoCardEntity) {
        MemoCardEntity createdMemo = memoCardService.addMemoCarte(memoCardEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMemo);
    }*/

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/creation")
    public void createMemoCard(@RequestBody MemoCardEntity memoCardEntity) {
      this.memoCardService.addMemoCarte(memoCardEntity);
    }

    @DeleteMapping("/suppression/{id}")
    public ResponseEntity<Void> deleteMemoCardById(@PathVariable long id) {
        try {
            memoCardService.deleteMemoCarte(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la suppression du mémo avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // Gérer l'exception
            return null;
        }
    }

    private <T> T convertJsonToObject(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
