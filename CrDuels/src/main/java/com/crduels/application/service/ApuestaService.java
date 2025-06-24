package com.crduels.application.service;

import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.EstadoApuesta;
import com.crduels.infrastructure.dto.rq.ApuestaRequest;
import com.crduels.infrastructure.dto.rs.ApuestaResponse;
import com.crduels.infrastructure.mapper.ApuestaMapper;
import com.crduels.infrastructure.repository.ApuestaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;

    @Transactional
    public Apuesta crearApuesta(@Valid ApuestaRequest dto) {
        Apuesta apuesta = ApuestaMapper.toEntity(dto);
        return apuestaRepository.save(apuesta);
    }

    public ApuestaResponse cambiarEstado(UUID id, EstadoApuesta estado) {
        Apuesta apuesta = apuestaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apuesta no encontrada"));
        apuesta.setEstado(estado);
        Apuesta saved = apuestaRepository.save(apuesta);
        return ApuestaMapper.toDto(saved);
    }
}
