package com.cines.premieres.controller;

import com.cines.premieres.dto.SessionRequest;
import com.cines.premieres.dto.SessionResponse;
import com.cines.premieres.service.SessionTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/session")
@Tag(name = "Session", description = "Emisión del JWT de sesión de invitado")
public class SessionController {

    private final SessionTokenService sessionTokenService;

    public SessionController(SessionTokenService sessionTokenService) {
        this.sessionTokenService = sessionTokenService;
    }

    @PostMapping
    @Operation(summary = "Crea una sesión de invitado y emite el JWT usado por candystore y complete")
    public SessionResponse createSession(@Valid @RequestBody SessionRequest request) {
        return sessionTokenService.createGuestSession();
    }
}
