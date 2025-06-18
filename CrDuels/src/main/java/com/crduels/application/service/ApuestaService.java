package com.crduels.application.service;

import com.crduels.application.dto.ApuestaRequestDto;
import com.crduels.application.dto.ApuestaResponseDto;
import com.crduels.application.mapper.ApuestaMapper;
import com.crduels.domain.model.Apuesta;
import com.crduels.domain.model.EstadoApuesta;
import com.crduels.infrastructure.repository.ApuestaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final ApuestaMapper apuestaMapper;

    public ApuestaService(ApuestaRepository apuestaRepository, ApuestaMapper apuestaMapper) {
        this.apuestaRepository = apuestaRepository;
        this.apuestaMapper = apuestaMapper;
    }

    public ApuestaResponseDto crearApuesta(ApuestaRequestDto dto) {
        Apuesta apuesta = apuestaMapper.toEntity(dto);
        apuesta.setEstado(EstadoApuesta.PENDIENTE);
        apuesta.setCreadoEn(LocalDateTime.now());
        Apuesta saved = apuestaRepository.save(apuesta);
        return apuestaMapper.toDto(saved);
    }

    public List<ApuestaResponseDto> listarPendientesPorModo(String modoJuego) {
        return apuestaRepository.findByEstado(EstadoApuesta.PENDIENTE).stream()
                .filter(a -> a.getModoJuego().equalsIgnoreCase(modoJuego))
                .map(apuestaMapper::toDto)
                .collect(Collectors.toList());
    }

    public ApuestaResponseDto cambiarEstado(UUID id, EstadoApuesta estado) {
        Apuesta apuesta = apuestaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apuesta no encontrada"));
        apuesta.setEstado(estado);
        Apuesta saved = apuestaRepository.save(apuesta);
        return apuestaMapper.toDto(saved);
    }
}
