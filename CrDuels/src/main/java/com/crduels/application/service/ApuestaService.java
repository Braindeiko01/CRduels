package com.crduels.application.service;

import com.crduels.infrastructure.dto.rq.ApuestaRequest;
import com.crduels.infrastructure.dto.rs.ApuestaResponse;
import com.crduels.infrastructure.mapper.ApuestaMapper;
import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.EstadoApuesta;
import com.crduels.infrastructure.repository.ApuestaRepository;
import com.crduels.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final ApuestaMapper apuestaMapper;
    private final UsuarioRepository usuarioRepository;

    public ApuestaResponse crearApuesta(ApuestaRequest dto) {
        Apuesta apuesta = apuestaMapper.toEntity(dto);
        apuesta.setEstado(EstadoApuesta.PENDIENTE);
        apuesta.setCreadoEn(LocalDateTime.now());

        if (apuesta.getJugador1() != null) {
            usuarioRepository.findById(apuesta.getJugador1().getId()).ifPresent(u -> {
                if (u.getSaldo().compareTo(apuesta.getMonto()) < 0) {
                    throw new IllegalArgumentException("Saldo insuficiente para el jugador");
                }
                u.setSaldo(u.getSaldo().subtract(apuesta.getMonto()));
                usuarioRepository.save(u);
            });
        }
        if (apuesta.getJugador2() != null) {
            usuarioRepository.findById(apuesta.getJugador2().getId()).ifPresent(u -> {
                if (u.getSaldo().compareTo(apuesta.getMonto()) < 0) {
                    throw new IllegalArgumentException("Saldo insuficiente para el jugador");
                }
                u.setSaldo(u.getSaldo().subtract(apuesta.getMonto()));
                usuarioRepository.save(u);
            });
        }

        Apuesta saved = apuestaRepository.save(apuesta);
        return apuestaMapper.toDto(saved);
    }

    public List<ApuestaResponse> listarPendientesPorModo(String modoJuego) {
        return apuestaRepository.findByEstado(EstadoApuesta.PENDIENTE).stream()
                .filter(a -> a.getModoJuego().equalsIgnoreCase(modoJuego))
                .map(apuestaMapper::toDto)
                .collect(Collectors.toList());
    }

    public ApuestaResponse cambiarEstado(UUID id, EstadoApuesta estado) {
        Apuesta apuesta = apuestaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apuesta no encontrada"));
        apuesta.setEstado(estado);
        Apuesta saved = apuestaRepository.save(apuesta);
        return apuestaMapper.toDto(saved);
    }
}
