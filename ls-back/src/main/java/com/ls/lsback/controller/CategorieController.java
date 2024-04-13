package com.ls.lsback.controller;

import com.ls.lsback.entity.CategorieEntity;
import com.ls.lsback.service.CategorieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorie")
@Slf4j
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/liste")
    public ResponseEntity<List<CategorieEntity>> listCategorie() {
        List<CategorieEntity> categories = categorieService.listCategorie();
        if (categories.isEmpty()) {
            log.info("Pas de catégorie trouvée");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieEntity> getCategorieById(@PathVariable("id") long id) {
        CategorieEntity cat = categorieService.getCategorie(id);
        if (cat == null) {
            log.info("Catégorie avec ID {} non trouvée", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

}
