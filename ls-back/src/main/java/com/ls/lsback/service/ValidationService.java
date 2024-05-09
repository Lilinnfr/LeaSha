package com.ls.lsback.service;

import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.entity.ValidationEntity;
import com.ls.lsback.repository.ValidationRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class ValidationService {

    private final ValidationRepository validationRepository;
    private final NotificationService notificationService;

    public ValidationService(ValidationRepository validationRepository, NotificationService notificationService) {
        this.validationRepository = validationRepository;
        this.notificationService = notificationService;
    }

    public void saveUser(UtilisateurEntity utilisateurEntity) {
        // on veut une entité vide à la base
        ValidationEntity validation = new ValidationEntity();
        // on dit que cette validation est lié à notre utilisateur
        validation.setUtilisateur(utilisateurEntity);
        // on récupère le moment de la création
        Instant creation = Instant.now();
        validation.setCreation(creation);
        // le moment de l'expiration du code correspond  à  l'instant de la création + 10 minutes
        Instant expiration = creation.plus(10, ChronoUnit.MINUTES);
        validation.setExpiration(expiration);

        // définition du code d'activation aléatoire
        Random random = new Random();
        // on veut qu'il soit à 6 chiffres
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        // on le définit et on l'enregistre
        validation.setCode(code);
        this.validationRepository.save(validation);
        // grâce à notre service de validation, on va pouvoir envoyer l'email à l'utilisateur avec son code
        this.notificationService.send(validation);
    }

    public ValidationEntity getValidationByCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }

}
