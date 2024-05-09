package com.ls.lsback.service.impl;

import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.entity.ValidationEntity;
import com.ls.lsback.repository.UtilisateurRepository;
import com.ls.lsback.service.UtilisateurService;
import com.ls.lsback.service.ValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.el.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, BCryptPasswordEncoder passwordEncoder, ValidationService validationService) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
    }

    public List<UtilisateurEntity> listUtilisateur() {
        return utilisateurRepository.findAll();
    }

    @Override
    public UtilisateurEntity addUtilisateur(UtilisateurEntity utilisateurEntity) {
        // on vérifie le format de l'email
        if (!utilisateurEntity.getEmail().contains("@")) {
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
        utilisateurEntity.setMotDePasse(encryptedPassword);
        // et enfin, on enregistre le nouvel utilisateur
        UtilisateurEntity nouvelUtilisateur = utilisateurRepository.save(utilisateurEntity);
        // utilise le service de validation
        validationService.saveUser(nouvelUtilisateur);
        return nouvelUtilisateur;
    }

    @Override
    public void activation(Map<String, String> activation) {
        // stocke la validation
        ValidationEntity validation = this.validationService.getValidationByCode(activation.get("code"));
        // si l'horaire où l'utilisateur vient aciver son compte arrive après l'horaire d'expiration, on envoie une exception
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

    @Override
    public void deleteUtilisateur(long id) {
        Optional<UtilisateurEntity> memo = utilisateurRepository.findById(id);
        if (memo.isPresent()) {
            utilisateurRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("L'utilisateur avec l'ID " + id + " n'existe pas.");
        }
    }

}
