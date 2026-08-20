package com.cines.candystore.config;

import com.cines.common.security.JwtAuthenticationFilter;
import com.cines.common.security.JwtTokenProvider;
import com.cines.common.security.SecurityConfigSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CandystoreSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public CandystoreSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtTokenProvider);
        SecurityConfigSupport.applyStatelessJwtConfig(http, jwtFilter,
                "/swagger-ui/**", "/v3/api-docs/**", "/actuator/health");
        return http.build();
    }
}
