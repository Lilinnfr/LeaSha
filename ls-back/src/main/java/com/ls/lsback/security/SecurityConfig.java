package com.ls.lsback.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
        http
                .csrf((AbstractHttpConfigurer::disable))
                .authorizeHttpRequests(auth -> auth
                        // si je fait un post au niveau de l'inscription, on laisse passer
                        .requestMatchers(HttpMethod.POST,"utilisateur/inscription").permitAll()
                        // sinon il faut être authentifié
                        .anyRequest().authenticated()
                )
             /*   .formLogin(login -> login
                        .loginPage("/inscription")
                );*/
         .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



