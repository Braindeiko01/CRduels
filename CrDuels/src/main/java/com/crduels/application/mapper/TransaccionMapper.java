package com.crduels.application.mapper;

import com.crduels.application.dto.TransaccionRequestDto;
import com.crduels.application.dto.TransaccionResponseDto;
import com.crduels.domain.model.Transaccion;

import org.springframework.stereotype.Component;

@Component
public class TransaccionMapper {

    public Transaccion toEntity(TransaccionRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Transaccion transaccion = new Transaccion();
        transaccion.setUsuarioId(dto.getUsuarioId());
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
                .usuarioId(entity.getUsuarioId())
                .monto(entity.getMonto())
                .tipo(entity.getTipo())
                .estado(entity.getEstado())
                .creadoEn(entity.getCreadoEn())
                .build();
    }
}
