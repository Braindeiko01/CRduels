package com.crduels.application.service;

import com.crduels.domain.entity.Jugador;
import com.crduels.infrastructure.dto.rq.JugadorRequest;
import com.crduels.infrastructure.dto.rs.JugadorResponse;
import com.crduels.infrastructure.exception.DuplicateUserException;
import com.crduels.infrastructure.mapper.JugadorMapper;
import com.crduels.infrastructure.repository.JugadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JugadorService {

    private final JugadorRepository jugadorRepository;
    private final JugadorMapper jugadorMapper;

    public JugadorResponse registrarJugador(JugadorRequest dto) {
        if (jugadorRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateUserException("El email ya está registrado");
        }

        if (jugadorRepository.existsByTelefono(dto.getTelefono())) {
            throw new DuplicateUserException("El teléfono ya está registrado");
        }
        // Mapeo de DTO A entidad
        Jugador jugador = jugadorMapper.toEntity(dto);
        Jugador saved = jugadorRepository.save(jugador);
        return jugadorMapper.toDto(saved);
    }

    public Optional<JugadorResponse> obtenerPorId(String id) {
        return jugadorRepository.findById(id)
                .map(jugadorMapper::toDto);
    }

}
