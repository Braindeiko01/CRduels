package com.crduels.application.service;

import com.crduels.application.dto.ApuestaRequestDto;
import com.crduels.application.dto.ApuestaResponseDto;
import com.crduels.application.mapper.ApuestaMapper;
import com.crduels.domain.model.Apuesta;
import com.crduels.domain.model.EstadoApuesta;
import com.crduels.infrastructure.repository.ApuestaRepository;
import com.crduels.infrastructure.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final ApuestaMapper apuestaMapper;
    private final UsuarioRepository usuarioRepository;

    public ApuestaService(ApuestaRepository apuestaRepository,
                          ApuestaMapper apuestaMapper,
                          UsuarioRepository usuarioRepository) {
        this.apuestaRepository = apuestaRepository;
        this.apuestaMapper = apuestaMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public ApuestaResponseDto crearApuesta(ApuestaRequestDto dto) {
        log.debug("Creando apuesta {}", dto);
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
        log.debug("Apuesta creada con id {}", saved.getId());
        return apuestaMapper.toDto(saved);
    }

    public List<ApuestaResponseDto> listarPendientesPorModo(String modoJuego) {
        log.debug("Listando apuestas pendientes para modo {}", modoJuego);
        return apuestaRepository.findByEstado(EstadoApuesta.PENDIENTE).stream()
                .filter(a -> a.getModoJuego().equalsIgnoreCase(modoJuego))
                .map(apuestaMapper::toDto)
                .collect(Collectors.toList());
    }

    public ApuestaResponseDto cambiarEstado(UUID id, EstadoApuesta estado) {
        log.debug("Cambiando estado de apuesta {} a {}", id, estado);
        Apuesta apuesta = apuestaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apuesta no encontrada"));
        apuesta.setEstado(estado);
        Apuesta saved = apuestaRepository.save(apuesta);
        log.debug("Estado actualizado para apuesta {}", id);
        return apuestaMapper.toDto(saved);
    }
}
