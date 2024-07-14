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
        try {
            List<UtilisateurEntity> utilisateurs = utilisateurService.listUtilisateur();
            if (utilisateurs.isEmpty()) {
                log.info("Pas d'utilisateur trouvé");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des utilisateurs", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/inscription")
    public ResponseEntity<UtilisateurEntity> createUtilisateur(@RequestBody UtilisateurEntity utilisateurEntity) {
        try {
            UtilisateurEntity nouvelUtilisateur = utilisateurService.addUtilisateur(utilisateurEntity);
            return new ResponseEntity<>(nouvelUtilisateur, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Erreur lors de l'inscription de l'utilisateur", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/suppression/{id}")
    public ResponseEntity<Void> deleteUtilisateurById(@PathVariable long id) {
        try {
            utilisateurService.deleteUtilisateur(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de la suppression de l'utilisateur", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/activation")
    public ResponseEntity<Void> activation(@RequestBody Map<String, String> activation) {
        try {
            this.utilisateurService.activation(activation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Une erreur s'est produite lors de l'activation", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/connexion")
    public ResponseEntity<Map<String, String>> connexion(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            final Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password())
            );
            if (authenticate.isAuthenticated()) {
                return new ResponseEntity<>(this.jwtService.generate(authenticationDTO.username()), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("Erreur lors de la connexion", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/deconnexion")
    public ResponseEntity<Void> deconnexion() {
        try {
            this.jwtService.deconnexion();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erreur lors de la déconnexion", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/modificationMdp")
    public ResponseEntity<Void> modifierMdp(@RequestBody Map<String, String> activation) {
        try {
            this.utilisateurService.changePassword(activation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erreur lors de la modification du mot de passe", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nouveauMdp")
    public ResponseEntity<Void> nouveauMdp(@RequestBody Map<String, String> activation) {
        try {
            this.utilisateurService.newPassword(activation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erreur lors de la réinitialisation du mot de passe", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        try {
            Map<String, String> tokens = this.jwtService.refreshToken(refreshTokenRequest);
            return new ResponseEntity<>(tokens, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erreur lors du rafraîchissement du token", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
