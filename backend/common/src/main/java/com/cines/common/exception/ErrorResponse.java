package com.cines.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final Instant timestamp = Instant.now();
    private final int status;
    private final String code;
    private final String message;
    private final String path;

    public ErrorResponse(int status, String code, String message, String path) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}
