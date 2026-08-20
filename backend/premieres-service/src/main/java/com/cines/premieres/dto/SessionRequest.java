package com.cines.premieres.dto;

import javax.validation.constraints.NotBlank;

public class SessionRequest {

    @NotBlank
    private String mode = "GUEST";

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
