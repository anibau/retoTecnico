package com.cines.premieres.service;

import com.cines.common.security.JwtTokenProvider;
import com.cines.premieres.dto.SessionResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionTokenService {

    private final JwtTokenProvider jwtTokenProvider;

    public SessionTokenService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public SessionResponse createGuestSession() {
        String subject = "guest-" + UUID.randomUUID();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "GUEST");

        String token = jwtTokenProvider.generateToken(subject, claims);
        return new SessionResponse(token, jwtTokenProvider.getExpirationSeconds());
    }
}
