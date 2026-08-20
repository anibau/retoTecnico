package com.cines.premieres.controller;

import com.cines.premieres.dto.PremiereDto;
import com.cines.premieres.service.PremiereService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/premieres")
@Tag(name = "Premieres", description = "Estrenos mostrados en la pantalla Home")
public class PremiereController {

    private final PremiereService premiereService;

    public PremiereController(PremiereService premiereService) {
        this.premiereService = premiereService;
    }

    @GetMapping
    @Operation(summary = "Lista los estrenos activos (imagen + texto) para la pantalla Home")
    public List<PremiereDto> getPremieres() {
        return premiereService.getPremieres();
    }
}
