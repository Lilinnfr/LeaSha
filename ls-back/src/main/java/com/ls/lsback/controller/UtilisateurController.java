package com.ls.lsback.controller;

import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.service.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins={"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping(value = "/utilisateur")
@Slf4j
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/liste")
    public ResponseEntity<List<UtilisateurEntity>> listUtilisateur() {
        List<UtilisateurEntity> utilisateurs = utilisateurService.listUtilisateur();
        if (utilisateurs.isEmpty()) {
            log.info("Pas d'utilisateur trouvé");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }
}
