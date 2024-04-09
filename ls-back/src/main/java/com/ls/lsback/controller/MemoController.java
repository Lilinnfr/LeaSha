package com.ls.lsback.controller;

import com.ls.lsback.entity.MemoEntity;
import com.ls.lsback.service.MemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/memo")
@Slf4j
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/liste")
    public ResponseEntity<List<MemoEntity>> listMemo() {
        List<MemoEntity> memos = memoService.listMemo();
        if (memos.isEmpty()) {
            log.info("Mémos non dispo");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(memos, HttpStatus.OK);
    }

}
