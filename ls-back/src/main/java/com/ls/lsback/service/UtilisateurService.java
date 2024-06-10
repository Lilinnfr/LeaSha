package com.ls.lsback.service;

import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.entity.ValidationEntity;
import com.ls.lsback.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UtilisateurService implements UserDetailsService {

    private UtilisateurRepository utilisateurRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;

    public List<UtilisateurEntity> listUtilisateur() {
        return utilisateurRepository.findAll();
    }

    public UtilisateurEntity addUtilisateur(UtilisateurEntity utilisateurEntity) {
        // on vérifie le format de l'email
        if (!utilisateurEntity.getEmail().contains("@")) {
            throw new RuntimeException("Le format est invalide");
        }
        if (!utilisateurEntity.getEmail().contains(".")) {
            throw new RuntimeException("Le format est invalide");
        }
        // on récupère l'email de l'utilisateur
        Optional<UtilisateurEntity> utilisateur = this.utilisateurRepository.findByEmail(utilisateurEntity.getEmail());
        if (utilisateur.isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        // on récupère le mot de passe utilisateur pour le crypter
        String encryptedPassword = this.passwordEncoder.encode(utilisateurEntity.getPassword());
        // puis on attribue le mot de passe crypté à l'utilisateur
        utilisateurEntity.setPassword(encryptedPassword);
        // et enfin, on enregistre le nouvel utilisateur
        UtilisateurEntity nouvelUtilisateur = utilisateurRepository.save(utilisateurEntity);
        // utilise le service de validation
        validationService.saveUser(nouvelUtilisateur);
        return nouvelUtilisateur;
    }

    public void activation(Map<String, String> activation) {
        // stocke la validation
        ValidationEntity validation = this.validationService.getValidationByCode(activation.get("code"));
        // si le moment où l'utilisateur vient activer son compte arrive après le moment d'expiration, on envoie une exception
        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Votre code a expiré");
        }
        // sinon on vient simplement chercher le bon utilisateur dans la bdd
        UtilisateurEntity activatedUser = this.utilisateurRepository.findById(validation.getUtilisateur().getId()).orElseThrow(() -> new RuntimeException("Utilisateur inconnu"));
        // le statut passe sur actif
        activatedUser.setActif(true);
        // on enregistre le nouvel utilisateur après son changement de statut
        this.utilisateurRepository.save(activatedUser);
    }

    public void deleteUtilisateur(long id) {
        Optional<UtilisateurEntity> memo = utilisateurRepository.findById(id);
        if (memo.isPresent()) {
            utilisateurRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("L'utilisateur avec l'ID " + id + " n'existe pas.");
        }
    }

    @Override
    public UtilisateurEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        // on va chercher l'utilisateur
        return this.utilisateurRepository
                .findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur ne correspond à cet identifiant"));
    }

    public void changePassword(Map<String, String> param) {
        UtilisateurEntity utilisateur = this.loadUserByUsername(param.get("email"));
        this.validationService.saveUser(utilisateur);
    }

    public void newPassword(Map<String, String> param) {
        // on va chercher l'utilisateur
        UtilisateurEntity utilisateur = this.loadUserByUsername(param.get("email"));
        // on récupère le code d'activation
        ValidationEntity validation = validationService.getValidationByCode(param.get("code"));
        // si l'email de l'utilisateur est le même que celui de la base de données
        if (validation.getUtilisateur().getEmail().equals(utilisateur.getEmail())) {
            // on récupère son nouveau mot de passe pour l'encrypter
            String encryptedPassword = this.passwordEncoder.encode(param.get("password"));
            utilisateur.setPassword(encryptedPassword);
            this.utilisateurRepository.save(utilisateur);
        }
    }

}