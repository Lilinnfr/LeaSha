package com.ls.lsback.controller;

import com.ls.lsback.entity.ListMemoEntity;
import com.ls.lsback.service.ListMemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins={"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping(value="/memoListe")
@Slf4j
public class ListMemoController {

    private final ListMemoService listMemoService;

    public ListMemoController(ListMemoService listMemoService) {
        this.listMemoService = listMemoService;
    }

    @GetMapping("/liste")
    public ResponseEntity<List<ListMemoEntity>> listMemoList() {
        List<ListMemoEntity> memos = listMemoService.listMemoListe();
        if (memos.isEmpty()) {
            log.info("Mémos non dispo");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(memos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListMemoEntity> getListMemoById(@PathVariable("id") long id) {
        ListMemoEntity memo = listMemoService.getMemoListe(id);
        if (memo == null) {
            log.info("Mémo avec id {} non trouvé", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(memo, HttpStatus.OK);
    }

    @PostMapping("/creation")
    public ResponseEntity<ListMemoEntity> createListMemo(@RequestBody ListMemoEntity listMemoEntity) {
        ListMemoEntity createdMemo = listMemoService.addMemoListe(listMemoEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMemo);
    }

    @DeleteMapping("/suppression/{id}")
    public ResponseEntity<Void> deleteListMemoById(@PathVariable long id) {
        boolean deletedMemo = listMemoService.deleteMemoListe(id);
        if (deletedMemo) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
