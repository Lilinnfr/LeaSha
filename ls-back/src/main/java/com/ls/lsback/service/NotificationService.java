package com.ls.lsback.service;

import com.ls.lsback.entity.ValidationEntity;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    JavaMailSender javaMailSender;
    public void send(ValidationEntity validation) {
        // on crée un nouvel email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // on choisit l'adresse email d'où part le message
        mailMessage.setFrom("chaumet.lydie@gmail.com");
        // adresse email à qui ce sera envoyé
        mailMessage.setTo(validation.getUtilisateur().getEmail());
        // on définit un objet pour l'email
        mailMessage.setSubject("Voici votre code d'activation");
        // puis le message
        String text = String.format(
                "Bonjour %s, voici votre code d'activation : %s",
                validation.getUtilisateur().getUsername(),
                validation.getCode()
        );
        mailMessage.setText(text);
        // et enfin on envoie l'email
        javaMailSender.send(mailMessage);
    }
}
