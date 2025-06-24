package com.crduels.infrastructure.mapper;

import com.crduels.domain.entity.Jugador;
import com.crduels.domain.entity.Transaccion;
import com.crduels.infrastructure.dto.rq.TransaccionRequest;
import com.crduels.infrastructure.dto.rs.TransaccionResponse;
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
        if (dto.getJugadorId() != null) {
            Jugador jugador = Jugador.builder().id(dto.getJugadorId()).build();
            builder.jugador(jugador);
        }
        return builder.build();
    }

    public TransaccionResponse toDto(Transaccion entity) {
        if (entity == null) {
            return null;
        }
        return TransaccionResponse.builder()
                .id(entity.getId())
                .jugadorId(entity.getJugador() != null ? entity.getJugador().getId() : null)
                .monto(entity.getMonto())
                .tipo(entity.getTipo())
                .estado(entity.getEstado())
                .creadoEn(entity.getCreadoEn())
                .build();
    }
}
