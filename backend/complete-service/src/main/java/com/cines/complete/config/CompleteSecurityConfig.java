package com.cines.complete.config;

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
public class CompleteSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public CompleteSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtTokenProvider);
        SecurityConfigSupport.applyStatelessJwtConfig(http, filter,
                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/actuator/health");
        return http.build();
    }
}
