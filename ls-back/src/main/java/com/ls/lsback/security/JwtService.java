package com.ls.lsback.security;

import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.service.UtilisateurService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {

    private UtilisateurService utilisateurService;
    private final String ENCRYPTION_KEY = "bG9zc3BsdXJhbHN3dW5nZW5lbXlwb2xpY2VtYW5wZXJzb25hbGluc2lkZW5ld3NwYXA=";
    public Map<String, String> generate(String username) {
        // on s'assure que le username est dans la base de données
        UtilisateurEntity utilisateur = (UtilisateurEntity) this.utilisateurService.loadUserByUsername(username);
        // on appelle la méthode pour générer le jwt pour l'utilisateur
        return this.generateJwt(utilisateur);
    }

    private Map<String, String> generateJwt(UtilisateurEntity utilisateur) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + 30 * 60 * 1000;

        // on définit les claims
        Map<String, Object> claims = Map.of(
                "username", utilisateur.getUsername(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, utilisateur.getEmail()
        );
        // on définit le jwt
        String bearer = Jwts.builder()
                // date de création
                .setIssuedAt(new Date(currentTime))
                // date d'expiration
                .setExpiration(new Date(expirationTime))
                // personne pour laquelle le token est généré
                .setSubject(utilisateur.getEmail())
                // informations sur l'utilisateur
                .setClaims(claims)
                // algorithme de signature
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    // récupération du username en fonction du token donné
    public String readUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        // va chercher les claims depuis la date
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims((token));
        return function.apply(claims);
    }

    // récupération de tous les claims grâce au token
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
