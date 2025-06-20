package com.crduels.application.service;

import com.crduels.application.dto.TransaccionRequestDto;
import com.crduels.application.dto.TransaccionResponseDto;
import com.crduels.application.mapper.TransaccionMapper;
import com.crduels.domain.model.EstadoTransaccion;
import com.crduels.domain.model.Transaccion;
import com.crduels.domain.model.TipoTransaccion;
import com.crduels.domain.model.Usuario;
import com.crduels.infrastructure.repository.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepository;

    public TransaccionService(TransaccionRepository transaccionRepository,
                              TransaccionMapper transaccionMapper,
                              UsuarioRepository usuarioRepository) {
        this.transaccionRepository = transaccionRepository;
        this.transaccionMapper = transaccionMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public TransaccionResponseDto registrarTransaccion(TransaccionRequestDto dto) {
        Transaccion transaccion = transaccionMapper.toEntity(dto);
        transaccion.setEstado(EstadoTransaccion.PENDIENTE);
        transaccion.setCreadoEn(LocalDateTime.now());
        Transaccion saved = transaccionRepository.save(transaccion);
        return transaccionMapper.toDto(saved);
    }

    public List<TransaccionResponseDto> listarPorUsuario(String usuarioId) {
        return transaccionRepository.findByUsuario_Id(usuarioId).stream()
                .map(transaccionMapper::toDto)
                .collect(Collectors.toList());
    }

    public TransaccionResponseDto cambiarEstado(UUID transaccionId, EstadoTransaccion estado) {
        Transaccion transaccion = transaccionRepository.findById(transaccionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaccion no encontrada"));

        if (transaccion.getEstado() != estado) {
            transaccion.setEstado(estado);

            if (estado == EstadoTransaccion.APROBADA) {
                Usuario usuario = usuarioRepository.findById(transaccion.getUsuario().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

                switch (transaccion.getTipo()) {
                    case DEPOSITO, PREMIO -> usuario.setSaldo(usuario.getSaldo().add(transaccion.getMonto()));
                    case RETIRO -> usuario.setSaldo(usuario.getSaldo().subtract(transaccion.getMonto()));
                }

                usuarioRepository.save(usuario);
            }
        }

        Transaccion saved = transaccionRepository.save(transaccion);
        return transaccionMapper.toDto(saved);
    }
}
