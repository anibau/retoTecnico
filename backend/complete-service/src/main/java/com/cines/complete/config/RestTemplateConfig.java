package com.cines.complete.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate payURestTemplate(RestTemplateBuilder builder) {
        // El sandbox de PayU LATAM suele tardar 8-11s en procesar una transacción real
        // (no una validación fallida, que responde rápido) - un timeout de 10s truena
        // intermitentemente por estar justo en el límite observado.
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }
}
