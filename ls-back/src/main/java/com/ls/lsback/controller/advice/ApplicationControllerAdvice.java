package com.ls.lsback.controller.advice;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {MalformedJwtException.class, SignatureException.class})
    public @ResponseBody
    ProblemDetail jwtValidationException(Exception exception) {
        ApplicationControllerAdvice.log.error(exception.getMessage(), exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Token invalide");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = BadCredentialsException.class)
    public @ResponseBody
    ProblemDetail badCredentialsException(BadCredentialsException exception) {
        ApplicationControllerAdvice.log.error(exception.getMessage(), exception);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Identifiants invalides");
        problemDetail.setProperty("erreur", "Nous n'avons pas pu vous identifier");
        return problemDetail;
    }

    // toutes les exceptions doivent atterrir au moins ici
    // on va retourner un code erreur et un message sous forme de chaine de caractères
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = Exception.class)
    public Map<String, String> exceptionHandler() {
        return Map.of("erreur", "Description");
    }
}
