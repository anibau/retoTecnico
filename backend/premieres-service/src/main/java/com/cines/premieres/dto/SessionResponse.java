package com.cines.premieres.dto;

public class SessionResponse {

    private String token;
    private String tokenType = "Bearer";
    private long expiresIn;

    public SessionResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
