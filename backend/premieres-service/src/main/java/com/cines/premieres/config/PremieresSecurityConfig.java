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
 * premieres-service es la puerta de entrada pública del flujo (Home + emisión de sesión),
 * por lo que no exige JWT en sus propios endpoints. Aun así se declara explícitamente
 * el filtro y el permitAll, ya que Spring Security por defecto bloquea todo con login form.
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
        SecurityConfigSupport.applyStatelessJwtConfig(http, jwtFilter, "/**");
        return http.build();
    }
}
