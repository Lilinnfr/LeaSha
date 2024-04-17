package com.ls.lsback.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ls.lsback.entity.MemoListEntity;
import com.ls.lsback.service.MemoListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins={"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping(value="/memoListe")
@Slf4j
public class MemoListController {

    private final MemoListService memoListService;
    private final ObjectMapper objectMapper;

    public MemoListController(MemoListService memoListService, ObjectMapper objectMapper) {
        this.memoListService = memoListService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/liste")
    public ResponseEntity<List<MemoListEntity>> listMemoList() {
        List<MemoListEntity> memos = memoListService.listMemoListe();
        if (memos.isEmpty()) {
            log.info("Mémos non dispo");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(memos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemoListEntity> getMemoListById(@PathVariable("id") long id) {
        MemoListEntity memo = memoListService.getMemoListe(id);
        if (memo == null) {
            log.info("Mémo avec ID {} non trouvé", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(memo, HttpStatus.OK);
    }

    @PostMapping("/creation")
    public ResponseEntity<MemoListEntity> createMemoList(@RequestBody MemoListEntity memoListEntity) {
        MemoListEntity createdMemo = memoListService.addMemoListe(memoListEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMemo);
    }

    @DeleteMapping("/suppression/{id}")
    public ResponseEntity<Void> deleteMemoListById(@PathVariable long id) {
        boolean deletedMemo = memoListService.deleteMemoListe(id);
        if (deletedMemo) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
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
