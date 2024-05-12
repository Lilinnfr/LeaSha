package com.ls.lsback.controller;

import com.ls.lsback.dto.AuthenticationDTO;
import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.security.JwtService;
import com.ls.lsback.service.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins={"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping(value = "/utilisateur")
@Slf4j
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UtilisateurController(UtilisateurService utilisateurService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.utilisateurService = utilisateurService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

    @DeleteMapping("/suppression/{id}")
    public ResponseEntity<Void> deleteUtilisateurById(@PathVariable long id) {
        try {
            utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la suppression de l'utilisateur avec l'ID : {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/inscription")
    public ResponseEntity<UtilisateurEntity> createUtilisateur(@RequestBody UtilisateurEntity utilisateurEntity) {
        UtilisateurEntity nouvelUtilisateur = utilisateurService.addUtilisateur(utilisateurEntity);
        return new ResponseEntity<>(nouvelUtilisateur, HttpStatus.CREATED);
    }

    @PostMapping("/activation")
    public void activation(@RequestBody Map<String, String> activation) {
        this.utilisateurService.activation(activation);
    }

    @PostMapping("/connexion")
    public Map<String, String> connexion(@RequestBody AuthenticationDTO authenticationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
        );
        if (authenticate.isAuthenticated()) {
            return this.jwtService.generate(authenticationDTO.username());
        }
        return null;
    }
}
