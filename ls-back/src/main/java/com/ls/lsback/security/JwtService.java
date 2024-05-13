package com.ls.lsback.security;

import com.ls.lsback.entity.JwtEntity;
import com.ls.lsback.entity.UtilisateurEntity;
import com.ls.lsback.repository.JwtRepository;
import com.ls.lsback.service.UtilisateurService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
@AllArgsConstructor
public class JwtService {

    private UtilisateurService utilisateurService;
    private static final String BEARER = "bearer";
    private final String ENCRYPTION_KEY = "bG9zc3BsdXJhbHN3dW5nZW5lbXlwb2xpY2VtYW5wZXJzb25hbGluc2lkZW5ld3NwYXA=";
    private JwtRepository jwtRepository;

    public Map<String, String> generate(String username) {
        // on s'assure que le username est dans la base de données
        UtilisateurEntity utilisateur = (UtilisateurEntity) this.utilisateurService.loadUserByUsername(username);
        this.disableTokens(utilisateur);
        // on appelle la méthode pour générer le jwt pour l'utilisateur
        Map<String, String> jwtMap = this.generateJwt(utilisateur);
        final JwtEntity jwt = JwtEntity
                .builder()
                .valeur(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .utilisateur(utilisateur)
                .build();
        this.jwtRepository.save(jwt);
        return jwtMap;
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

    public JwtEntity tokenByValue(String value) {
        return this.jwtRepository.findByValeurAndDesactiveAndExpire(value, false, false)
                .orElseThrow(() -> new RuntimeException("Token invalide ou inconnu"));
    }

    public void disableTokens(UtilisateurEntity utilisateur) {
        final List<JwtEntity> jwtList = this.jwtRepository.findUser(utilisateur.getEmail()).peek(
                jwt -> {
                    jwt.setExpire(true);
                    jwt.setDesactive(true);
                }
        ).collect(Collectors.toList());
        this.jwtRepository.saveAll(jwtList);
    }

    public void deconnexion() {
        // on récupère l'utilisateur connecté
        UtilisateurEntity utilisateur = (UtilisateurEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // le token trouvé doit avoir un utilisateur, être valide et non expiré
        JwtEntity jwt = this.jwtRepository.findUserValidToken(
                utilisateur.getEmail(),
                false,
                false)
                .orElseThrow(() -> new RuntimeException("Token invalide"));
        jwt.setExpire(true);
        jwt.setDesactive(true);
        this.jwtRepository.save(jwt);
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void removeUselessJwt() {
        log.info("Suppression des token à {}", Instant.now());
        this.jwtRepository.deleteAllByExpireAndDesactive(true, true);
    }
    
}
