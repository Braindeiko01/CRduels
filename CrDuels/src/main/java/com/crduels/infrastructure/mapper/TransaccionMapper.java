package com.crduels.infrastructure.mapper;

import com.crduels.infrastructure.dto.rq.TransaccionRequest;
import com.crduels.infrastructure.dto.rs.TransaccionResponse;
import com.crduels.domain.entity.Transaccion;
import com.crduels.domain.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class TransaccionMapper {

    public Transaccion toEntity(TransaccionRequest dto) {
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

    public TransaccionResponse toDto(Transaccion entity) {
        if (entity == null) {
            return null;
        }
        return TransaccionResponse.builder()
                .id(entity.getId())
                .usuarioId(entity.getUsuario() != null ? entity.getUsuario().getId() : null)
                .monto(entity.getMonto())
                .tipo(entity.getTipo())
                .estado(entity.getEstado())
                .creadoEn(entity.getCreadoEn())
                .build();
    }
}
