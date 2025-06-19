package com.crduels.application.service;

import com.crduels.application.dto.TransaccionRequestDto;
import com.crduels.application.dto.TransaccionResponseDto;
import com.crduels.application.mapper.TransaccionMapper;
import com.crduels.domain.model.EstadoTransaccion;
import com.crduels.domain.model.Transaccion;
import com.crduels.infrastructure.repository.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final TransaccionMapper transaccionMapper;

    public TransaccionService(TransaccionRepository transaccionRepository, TransaccionMapper transaccionMapper) {
        this.transaccionRepository = transaccionRepository;
        this.transaccionMapper = transaccionMapper;
    }

    public TransaccionResponseDto registrarTransaccion(TransaccionRequestDto dto) {
        Transaccion transaccion = transaccionMapper.toEntity(dto);
        transaccion.setEstado(EstadoTransaccion.PENDIENTE);
        transaccion.setCreadoEn(LocalDateTime.now());
        Transaccion saved = transaccionRepository.save(transaccion);
        return transaccionMapper.toDto(saved);
    }

    public List<TransaccionResponseDto> listarPorUsuario(UUID usuarioId) {
        return transaccionRepository.findByUsuarioId(usuarioId).stream()
                .map(transaccionMapper::toDto)
                .collect(Collectors.toList());
    }

    public TransaccionResponseDto cambiarEstado(UUID transaccionId, EstadoTransaccion estado) {
        Transaccion transaccion = transaccionRepository.findById(transaccionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaccion no encontrada"));
        transaccion.setEstado(estado);
        Transaccion saved = transaccionRepository.save(transaccion);
        return transaccionMapper.toDto(saved);
    }
}
