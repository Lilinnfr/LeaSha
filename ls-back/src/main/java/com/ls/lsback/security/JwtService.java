package com.ls.lsback.security;

import com.ls.lsback.entity.JwtEntity;
import com.ls.lsback.entity.RefreshTokenEntity;
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
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
@AllArgsConstructor
public class JwtService {

    private UtilisateurService utilisateurService;
    private static final String BEARER = "bearer";
    public static final String REFRESH = "refresh";
    public static final String TOKEN_INVALIDE = "Token invalide";
    private final String ENCRYPTION_KEY = "bG9zc3BsdXJhbHN3dW5nZW5lbXlwb2xpY2VtYW5wZXJzb25hbGluc2lkZW5ld3NwYXA=";
    private JwtRepository jwtRepository;

    // retourne un jwt et un refresh token pour l'utilisateur
    public Map<String, String> generate(String username) {
        // on s'assure que le username est dans la base de données
        UtilisateurEntity utilisateur = this.utilisateurService.loadUserByUsername(username);
        this.disableTokens(utilisateur);
        // on appelle la méthode pour générer le jwt pour l'utilisateur
        final Map<String, String> jwtMap = new java.util.HashMap<>(this.generateJwt(utilisateur));
        // on définit le refresh token
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .valeur(UUID.randomUUID().toString())
                .expire(false)
                .creation(Instant.now())
                .expiration(Instant.now().plusMillis(30 *60 *1000))
                .build();


        final JwtEntity jwt = JwtEntity
                .builder()
                .valeur(jwtMap.get(BEARER))
                .desactive(false)
                .expire(false)
                .utilisateur(utilisateur)
                .refreshToken(refreshToken)
                .build();

        this.jwtRepository.save(jwt);
        jwtMap.put(REFRESH, refreshToken.getValeur());
        return jwtMap;
    }

    // génère un jwt pour l'utilisateur
    private Map<String, String> generateJwt(UtilisateurEntity utilisateur) {
        final long currentTime = System.currentTimeMillis();
        // il faudra remettre à une minute (60 * 1000) ou 30 minutes (30 * 60 * 1000)
        final long expirationTime = currentTime + (30L * 24 * 60 * 60 * 1000);

        log.info("JWT généré: {}", utilisateur.getEmail());
        log.info("Current time: {}", new Date(currentTime));
        log.info("Expiration time: {}", new Date(expirationTime));

        // on définit les claims
        final Map<String, Object> claims = Map.of(
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
        log.info("JWT généré : {}", bearer);
        return Map.of(BEARER, bearer);
    }

    // retourne la clé de signature à partir de la clé de chiffrement
    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    // récupération du username en fonction du token donné
    public String readUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    // vérifie si un token a expiré. true si le token a expiré, sinon false
    public boolean isTokenExpired(String token) {
        // va chercher les claims depuis la date
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        log.info("Token expirationDate: {}", expirationDate);
        log.info("Current time: {}", new Date());
        return expirationDate.before(new Date());
    }

    // récupère une "claim" spécifique à partir d'un token
    // token, le token à analyser
    // function, la fonction pour extraire la claim
    // <T>, le type de la claim
    // retourne la claim extraite
    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims((token));
        return function.apply(claims);
    }

    // récupération de toutes les "claims" à partir d'un token
    // token, le token en question
    // retourne les "claims" contenues dans le token
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // récupère un jwt à partir de sa valeur
    public JwtEntity tokenByValue(String value) {
        JwtEntity jwtEntity = this.jwtRepository.findByValeurAndDesactiveAndExpire(value, false, false)
                .orElseThrow(() -> new RuntimeException("Token invalide ou inconnu"));
        return jwtEntity;
    }

    // désactive tous les tokens d'un utilisateur
    public void disableTokens(UtilisateurEntity utilisateur) {
        final List<JwtEntity> jwtList = this.jwtRepository.findUser(utilisateur.getEmail()).peek(
                jwt -> {
                    jwt.setExpire(true);
                    jwt.setDesactive(true);
                }
        ).collect(Collectors.toList());
        this.jwtRepository.saveAll(jwtList);
    }

    // déconnecte l'utilisateur et désactive son token
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

    // on supprime les tokens inactifs toutes les minutes
    @Scheduled(cron = "0 */1 * * * *")
    public void removeUselessJwt() {
        log.info("Suppression des tokens à {}", Instant.now());
        this.jwtRepository.deleteAllByExpireAndDesactive(true, true);
    }

    // rafraichit le token
    public Map<String, String> refreshToken(Map<String, String> refreshTokenRequest) {
        final JwtEntity jwt = this.jwtRepository.findByRefreshToken(refreshTokenRequest.get(REFRESH))
                .orElseThrow(() -> new RuntimeException(TOKEN_INVALIDE));
        if (jwt.getRefreshToken().isExpire() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())) {
            throw new RuntimeException(TOKEN_INVALIDE);
        }
        this.disableTokens(jwt.getUtilisateur());
        return this.generate(jwt.getUtilisateur().getEmail());
    }
}
