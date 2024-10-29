package com.main.emailverification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/verify/**").permitAll()  // Allow unauthenticated access to verification endpoints
                        .anyRequest().authenticated()  // Require authentication for all other requests
                )
                .sessionManagement(session -> session.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable());  // Disable form login

        return http.build();
    }
}
