package com.crduels.application.mapper;

import com.crduels.application.dto.ApuestaRequestDto;
import com.crduels.application.dto.ApuestaResponseDto;
import com.crduels.domain.model.Apuesta;
import org.springframework.stereotype.Component;

@Component
public class ApuestaMapper {

    public Apuesta toEntity(ApuestaRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Apuesta apuesta = new Apuesta();
        apuesta.setJugador1Id(dto.getJugador1Id());
        apuesta.setJugador2Id(dto.getJugador2Id());
        apuesta.setMonto(dto.getMonto());
        apuesta.setModoJuego(dto.getModoJuego());
        return apuesta;
    }

    public ApuestaResponseDto toDto(Apuesta entity) {
        if (entity == null) {
            return null;
        }
        return ApuestaResponseDto.builder()
                .id(entity.getId())
                .monto(entity.getMonto())
                .modoJuego(entity.getModoJuego())
                .estado(entity.getEstado())
                .creadoEn(entity.getCreadoEn())
                .build();
    }

}
