package com.ls.lsback.controller;

import com.ls.lsback.entity.CategorieEntity;
import com.ls.lsback.service.CategorieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins={"http://localhost:4200", "http://localhost:8080"})
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
        CategorieEntity categorie = categorieService.getCategorie(id);
        if (categorie == null) {
            log.info("Catégorie avec ID {} non trouvée", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categorie, HttpStatus.OK);
    }

}
