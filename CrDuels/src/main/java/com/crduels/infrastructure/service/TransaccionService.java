package com.crduels.infrastructure.service;

import com.crduels.infrastructure.dto.TransaccionRequestDto;
import com.crduels.infrastructure.dto.TransaccionResponseDto;
import com.crduels.infrastructure.mapper.TransaccionMapper;
import com.crduels.domain.entity.EstadoTransaccion;
import com.crduels.domain.entity.Transaccion;
import com.crduels.domain.entity.Usuario;
import com.crduels.domain.repository.TransaccionRepository;
import com.crduels.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final TransaccionMapper transaccionMapper;
    private final UsuarioRepository usuarioRepository;

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
                .toList();
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
