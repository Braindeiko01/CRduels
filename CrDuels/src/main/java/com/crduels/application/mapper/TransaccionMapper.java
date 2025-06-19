package com.crduels.application.mapper;

import com.crduels.application.dto.TransaccionRequestDto;
import com.crduels.application.dto.TransaccionResponseDto;
import com.crduels.domain.model.Transaccion;
import com.crduels.domain.model.Usuario;

import org.springframework.stereotype.Component;

@Component
public class TransaccionMapper {

    public Transaccion toEntity(TransaccionRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Transaccion transaccion = new Transaccion();
        if (dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            transaccion.setUsuario(usuario);
        }
        transaccion.setMonto(dto.getMonto());
        transaccion.setTipo(dto.getTipo());
        return transaccion;
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
