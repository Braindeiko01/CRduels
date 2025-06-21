package com.crduels.infrastructure.service;

import com.crduels.infrastructure.dto.PartidaRequestDto;
import com.crduels.infrastructure.dto.PartidaResponseDto;
import com.crduels.infrastructure.mapper.PartidaMapper;
import com.crduels.domain.entity.*;
import com.crduels.domain.repository.ApuestaRepository;
import com.crduels.domain.repository.PartidaRepository;
import com.crduels.domain.repository.TransaccionRepository;
import com.crduels.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final PartidaMapper partidaMapper;
    private final ApuestaRepository apuestaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TransaccionRepository transaccionRepository;

    public PartidaService(PartidaRepository partidaRepository,
                          PartidaMapper partidaMapper,
                          ApuestaRepository apuestaRepository,
                          UsuarioRepository usuarioRepository,
                          TransaccionRepository transaccionRepository) {
        this.partidaRepository = partidaRepository;
        this.partidaMapper = partidaMapper;
        this.apuestaRepository = apuestaRepository;
        this.usuarioRepository = usuarioRepository;
        this.transaccionRepository = transaccionRepository;
    }

    public PartidaResponseDto registrarPartida(PartidaRequestDto dto) {
        Partida partida = partidaMapper.toEntity(dto);
        partida.setValidada(false);
        Partida saved = partidaRepository.save(partida);
        return partidaMapper.toDto(saved);
    }

    public Optional<PartidaResponseDto> obtenerPorApuestaId(UUID apuestaId) {
        return partidaRepository.findByApuesta_Id(apuestaId).map(partidaMapper::toDto);
    }

    public PartidaResponseDto marcarComoValidada(UUID partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new IllegalArgumentException("Partida no encontrada"));
        partida.setValidada(true);
        partida.setValidadaEn(LocalDateTime.now());
        if (partida.getGanador() != null && partida.getApuesta() != null) {
            Apuesta apuesta = apuestaRepository.findById(partida.getApuesta().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Apuesta no encontrada"));

            Transaccion premio = new Transaccion();
            premio.setUsuario(partida.getGanador());
            premio.setMonto(apuesta.getMonto().multiply(java.math.BigDecimal.valueOf(2)));
            premio.setTipo(TipoTransaccion.PREMIO);
            premio.setEstado(EstadoTransaccion.APROBADA);
            premio.setCreadoEn(LocalDateTime.now());
            transaccionRepository.save(premio);

            usuarioRepository.findById(partida.getGanador().getId()).ifPresent(u -> {
                u.setSaldo(u.getSaldo().add(premio.getMonto()));
                usuarioRepository.save(u);
            });
        }

        Partida saved = partidaRepository.save(partida);
        return partidaMapper.toDto(saved);
    }
}
