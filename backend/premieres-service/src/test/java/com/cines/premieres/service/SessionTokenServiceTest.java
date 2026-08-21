package com.cines.premieres.service;

import com.cines.common.security.JwtProperties;
import com.cines.common.security.JwtTokenProvider;
import com.cines.premieres.dto.SessionResponse;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionTokenServiceTest {

    private JwtTokenProvider jwtTokenProvider;
    private SessionTokenService sessionTokenService;

    @BeforeEach
    void setUp() {
        JwtProperties properties = new JwtProperties();
        properties.setSecret("test-only-secret-key-must-be-long-enough-for-hs256");
        properties.setExpirationMs(1_800_000L);
        properties.setIssuer("premieres-service");

        jwtTokenProvider = new JwtTokenProvider(properties);
        sessionTokenService = new SessionTokenService(jwtTokenProvider);
    }

    @Test
    void createGuestSession_emitsValidTokenWithGuestRole() {
        SessionResponse session = sessionTokenService.createGuestSession();

        assertThat(session.getToken()).isNotBlank();
        assertThat(jwtTokenProvider.isValid(session.getToken())).isTrue();
        assertThat(session.getExpiresIn()).isEqualTo(1_800L);

        Claims claims = jwtTokenProvider.parseClaims(session.getToken());
        assertThat(claims.getSubject()).startsWith("guest-");
        assertThat(claims.get("role")).isEqualTo("GUEST");
        assertThat(claims.getIssuer()).isEqualTo("premieres-service");
    }

    @Test
    void createGuestSession_generatesUniqueSubjectPerCall() {
        SessionResponse first = sessionTokenService.createGuestSession();
        SessionResponse second = sessionTokenService.createGuestSession();

        String firstSubject = jwtTokenProvider.parseClaims(first.getToken()).getSubject();
        String secondSubject = jwtTokenProvider.parseClaims(second.getToken()).getSubject();

        assertThat(firstSubject).isNotEqualTo(secondSubject);
    }

    @Test
    void isValid_returnsFalseForTamperedToken() {
        SessionResponse session = sessionTokenService.createGuestSession();
        String tampered = session.getToken().substring(0, session.getToken().length() - 2) + "xx";

        assertThat(jwtTokenProvider.isValid(tampered)).isFalse();
    }
}
