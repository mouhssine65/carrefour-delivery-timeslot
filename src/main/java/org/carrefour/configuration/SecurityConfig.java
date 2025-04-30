package org.carrefour.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Désactiver CSRF pour simplifier (à activer en production si nécessaire)
            .authorizeHttpRequests()
            .requestMatchers("/api/timeslot/**", "/api/delivery/**").authenticated() // Protéger les endpoints
            .anyRequest().permitAll() // Autoriser les autres requêtes
            .and()
            .httpBasic(); // Utiliser l'authentification HTTP Basic

        return http.build();
    }
}
