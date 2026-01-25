package com.example.resilient_api.application.config;

import com.example.resilient_api.infrastructure.adapters.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        // Actuator - público
                        .pathMatchers("/actuator/**").permitAll()

                        // ===== ENDPOINTS ADMIN (solo isAdmin = true) =====
                        // Crear tecnología - solo admin
                        .pathMatchers(HttpMethod.POST, "/technology").hasRole("ADMIN")

                        // ===== ENDPOINTS PÚBLICOS/INTERNOS (sin autenticación de usuario) =====
                        // Estos endpoints son usados internamente por otros microservicios
                        .pathMatchers(HttpMethod.POST, "/technology/check-exists").permitAll()
                        .pathMatchers(HttpMethod.POST, "/technology/by-ids").permitAll()
                        .pathMatchers(HttpMethod.POST, "/technology/decrement-references").permitAll()

                        // Por defecto: permitir todo lo demás
                        .anyExchange().permitAll()
                )
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .build();
    }
}
