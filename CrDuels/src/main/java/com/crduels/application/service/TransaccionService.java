package com.crduels.application.service;

import com.crduels.domain.entity.EstadoTransaccion;
import com.crduels.domain.entity.Transaccion;
import com.crduels.domain.entity.Usuario;
import com.crduels.infrastructure.dto.rq.TransaccionRequest;
import com.crduels.infrastructure.dto.rs.TransaccionResponse;
import com.crduels.infrastructure.mapper.TransaccionMapper;
import com.crduels.infrastructure.repository.TransaccionRepository;
import com.crduels.infrastructure.repository.UsuarioRepository;
import com.crduels.application.events.TransaccionAprobadaEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final TransaccionMapper transaccionMapper;
    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TransaccionResponse registrarTransaccion(TransaccionRequest dto) {
        Transaccion transaccion = transaccionMapper.toEntity(dto);
        transaccion.setEstado(EstadoTransaccion.PENDIENTE);
        transaccion.setCreadoEn(LocalDateTime.now());
        Transaccion saved = transaccionRepository.save(transaccion);
        return transaccionMapper.toDto(saved);
    }

    public List<TransaccionResponse> listarPorUsuario(String usuarioId) {
        return transaccionRepository.findByUsuario_Id(usuarioId).stream()
                .map(transaccionMapper::toDto)
                .toList();
    }

    @Transactional
    public TransaccionResponse aprobarTransaccion(UUID id) {
        Transaccion transaccion = transaccionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaccion no encontrada"));

        if (EstadoTransaccion.APROBADA.equals(transaccion.getEstado())) {
            throw new IllegalArgumentException("La transaccion ya ha sido aprobada con anterioridad");
        }

        modificarSaldoUsuario(transaccion);

        transaccion.setEstado(EstadoTransaccion.APROBADA);
        Transaccion saved = transaccionRepository.save(transaccion);

        TransaccionResponse dto = transaccionMapper.toDto(saved);
        eventPublisher.publishEvent(new TransaccionAprobadaEvent(dto));
        return dto;
    }

    private void modificarSaldoUsuario(Transaccion transaccion) {
        Usuario usuario = usuarioRepository.findById(transaccion.getUsuario().getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        switch (transaccion.getTipo()) {
            case DEPOSITO, PREMIO -> usuario.setSaldo(usuario.getSaldo().add(transaccion.getMonto()));
            case RETIRO, APUESTA -> {
                if (usuario.getSaldo().compareTo(transaccion.getMonto()) < 0) {
                    throw new IllegalArgumentException("Saldo insuficiente para realizar la transacción");
                }
                usuario.setSaldo(usuario.getSaldo().subtract(transaccion.getMonto()));
            }
        }

        usuarioRepository.save(usuario);
    }
}
