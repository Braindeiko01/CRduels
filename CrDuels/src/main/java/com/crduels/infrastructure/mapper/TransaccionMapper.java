package com.crduels.infrastructure.mapper;

import com.crduels.infrastructure.dto.TransaccionRequestDto;
import com.crduels.infrastructure.dto.TransaccionResponseDto;
import com.crduels.domain.entity.Transaccion;
import com.crduels.domain.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class TransaccionMapper {

    public Transaccion toEntity(TransaccionRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Transaccion.TransaccionBuilder builder = Transaccion.builder()
                .monto(dto.getMonto())
                .tipo(dto.getTipo());
        if (dto.getUsuarioId() != null) {
            Usuario usuario = Usuario.builder().id(dto.getUsuarioId()).build();
            builder.usuario(usuario);
        }
        return builder.build();
    }

    public TransaccionResponseDto toDto(Transaccion entity) {
        if (entity == null) {
            return null;
        }
        return TransaccionResponseDto.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuario() != null ? entity.getUsuario().getId() : null)
                .monto(entity.getMonto())
                .tipo(entity.getTipo())
                .estado(entity.getEstado())
                .creadoEn(entity.getCreadoEn())
                .build();
    }
}
