package com.cines.premieres.config;

import com.cines.common.security.JwtAuthenticationFilter;
import com.cines.common.security.JwtTokenProvider;
import com.cines.common.security.SecurityConfigSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Solo la emisión de sesión de invitado (POST /api/v1/session) y los paths de
 * infraestructura quedan públicos; el listado de estrenos exige el mismo JWT
 * que candystore-service y complete-service, igual que el resto de la app.
 */
@Configuration
@EnableWebSecurity
public class PremieresSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public PremieresSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtTokenProvider);
        SecurityConfigSupport.applyStatelessJwtConfig(http, jwtFilter,
                "/api/v1/session", "/swagger-ui/**", "/v3/api-docs/**", "/actuator/health");
        return http.build();
    }
}
