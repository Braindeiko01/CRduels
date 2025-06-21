package com.crduels.application.service;

import com.crduels.application.dto.PartidaRequestDto;
import com.crduels.application.dto.PartidaResponseDto;
import com.crduels.application.mapper.PartidaMapper;
import com.crduels.domain.model.Partida;
import com.crduels.domain.model.Transaccion;
import com.crduels.domain.model.TipoTransaccion;
import com.crduels.domain.model.EstadoTransaccion;
import com.crduels.domain.model.Apuesta;
import com.crduels.infrastructure.repository.ApuestaRepository;
import com.crduels.infrastructure.repository.TransaccionRepository;
import com.crduels.infrastructure.repository.UsuarioRepository;
import com.crduels.infrastructure.repository.PartidaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
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
        log.debug("Registrando partida {}", dto);
        Partida partida = partidaMapper.toEntity(dto);
        partida.setValidada(false);
        Partida saved = partidaRepository.save(partida);
        log.debug("Partida registrada con id {}", saved.getId());
        return partidaMapper.toDto(saved);
    }

    public Optional<PartidaResponseDto> obtenerPorApuestaId(UUID apuestaId) {
        log.debug("Buscando partida por apuesta {}", apuestaId);
        return partidaRepository.findByApuesta_Id(apuestaId).map(partidaMapper::toDto);
    }

    public PartidaResponseDto marcarComoValidada(UUID partidaId) {
        log.debug("Marcando partida {} como validada", partidaId);
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
        log.debug("Partida {} validada", partidaId);
        return partidaMapper.toDto(saved);
    }
}
