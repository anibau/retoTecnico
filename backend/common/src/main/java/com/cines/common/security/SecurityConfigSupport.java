package com.cines.common.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public final class SecurityConfigSupport {

    private SecurityConfigSupport() {
    }

    /**
     * Config compartida por los 3 microservicios: sin sesión de servidor (JWT stateless),
     * CSRF deshabilitado (no hay formularios server-side), CORS delegado al bean
     * CorsConfigurationSource de {@link com.cines.common.config.CorsConfig}, preflight OPTIONS
     * siempre permitido (si no, el navegador nunca llega a enviar la petición real cuando
     * manda Content-Type/Authorization, que fuerzan preflight) y las rutas públicas indicadas.
     */
    public static void applyStatelessJwtConfig(HttpSecurity http, JwtAuthenticationFilter jwtFilter,
                                                String... permitAllPatterns) throws Exception {
        http.csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(auth -> {
                    auth.antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    if (permitAllPatterns.length > 0) {
                        auth.antMatchers(permitAllPatterns).permitAll();
                    }
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
