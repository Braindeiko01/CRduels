package com.crduels.application.service;

import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.EstadoTransaccion;
import com.crduels.domain.entity.TipoTransaccion;
import com.crduels.domain.entity.Transaccion;
import com.crduels.domain.entity.partida.Partida;
import com.crduels.infrastructure.dto.rs.PartidaResponse;
import com.crduels.infrastructure.mapper.PartidaMapper;
import com.crduels.infrastructure.repository.ApuestaRepository;
import com.crduels.infrastructure.repository.JugadorRepository;
import com.crduels.infrastructure.repository.PartidaRepository;
import com.crduels.infrastructure.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final PartidaMapper partidaMapper;
    private final ApuestaRepository apuestaRepository;
    private final JugadorRepository jugadorRepository;
    private final TransaccionRepository transaccionRepository;

    public Optional<PartidaResponse> obtenerPorApuestaId(UUID apuestaId) {
        return partidaRepository.findByApuesta_Id(apuestaId).map(partidaMapper::toDto);
    }

    @Transactional
    public PartidaResponse marcarComoValidada(UUID partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new IllegalArgumentException("Partida no encontrada"));
        partida.setValidada(true);
        partida.setValidadaEn(LocalDateTime.now());
        if (partida.getGanador() != null && partida.getApuesta() != null) {
            Apuesta apuesta = apuestaRepository.findById(partida.getApuesta().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Apuesta no encontrada"));

            Transaccion premio = new Transaccion();
            premio.setJugador(partida.getGanador());
            premio.setMonto(apuesta.getMonto().multiply(java.math.BigDecimal.valueOf(2)));
            premio.setTipo(TipoTransaccion.PREMIO);
            premio.setEstado(EstadoTransaccion.APROBADA);
            premio.setCreadoEn(LocalDateTime.now());
            transaccionRepository.save(premio);

            jugadorRepository.findById(partida.getGanador().getId()).ifPresent(u -> {
                u.setSaldo(u.getSaldo().add(premio.getMonto()));
                jugadorRepository.save(u);
            });
        }

        Partida saved = partidaRepository.save(partida);
        return partidaMapper.toDto(saved);
    }
}
