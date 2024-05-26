package com.ls.lsback.security;

import com.ls.lsback.entity.JwtEntity;
import com.ls.lsback.service.UtilisateurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {
    private UtilisateurService utilisateurService;
    private JwtService jwtService;
    private HandlerExceptionResolver handlerExceptionResolver;

    public JwtFilter(UtilisateurService utilisateurService, JwtService jwtService, HandlerExceptionResolver handlerExceptionResolver) {
        this.utilisateurService = utilisateurService;
        this.jwtService = jwtService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        JwtEntity jwtBdd = null;
        String username = null;
        boolean isTokenExpired = true;

        try {
            final String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);
                jwtBdd = this.jwtService.tokenByValue(token);
                // on vérifie que le token n'a pas expiré
                isTokenExpired = jwtService.isTokenExpired(token);
                // si ça n'a pas expiré, on vérifie le username
                if (!isTokenExpired) {
                    username = jwtService.readUsername(token);
                }
            }

            // s'il n'y a pas déjà de context d'authentification
            if (!isTokenExpired
                    && jwtBdd.getUtilisateur().getEmail().equals(username)
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = utilisateurService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
