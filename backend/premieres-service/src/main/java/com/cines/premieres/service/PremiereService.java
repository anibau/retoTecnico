package com.cines.premieres.service;

import com.cines.premieres.dto.PremiereDto;
import com.cines.premieres.repository.PremiereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PremiereService {

    private final PremiereRepository premiereRepository;

    public PremiereService(PremiereRepository premiereRepository) {
        this.premiereRepository = premiereRepository;
    }

    public List<PremiereDto> getPremieres() {
        return premiereRepository.findAllActive();
    }
}
