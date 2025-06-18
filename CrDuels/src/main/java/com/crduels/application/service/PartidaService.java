package com.crduels.application.service;

import com.crduels.application.dto.PartidaRequestDto;
import com.crduels.application.dto.PartidaResponseDto;
import com.crduels.application.mapper.PartidaMapper;
import com.crduels.domain.model.Partida;
import com.crduels.infrastructure.repository.PartidaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final PartidaMapper partidaMapper;

    public PartidaService(PartidaRepository partidaRepository, PartidaMapper partidaMapper) {
        this.partidaRepository = partidaRepository;
        this.partidaMapper = partidaMapper;
    }

    public PartidaResponseDto registrarPartida(PartidaRequestDto dto) {
        Partida partida = partidaMapper.toEntity(dto);
        partida.setValidada(false);
        Partida saved = partidaRepository.save(partida);
        return partidaMapper.toDto(saved);
    }

    public Optional<PartidaResponseDto> obtenerPorApuestaId(UUID apuestaId) {
        return partidaRepository.findByApuestaId(apuestaId).map(partidaMapper::toDto);
    }

    public PartidaResponseDto marcarComoValidada(UUID partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new IllegalArgumentException("Partida no encontrada"));
        partida.setValidada(true);
        partida.setValidadaEn(LocalDateTime.now());
        Partida saved = partidaRepository.save(partida);
        return partidaMapper.toDto(saved);
    }
}
