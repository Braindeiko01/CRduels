package com.crduels.application.mapper;

import com.crduels.application.dto.ApuestaRequestDto;
import com.crduels.application.dto.ApuestaResponseDto;
import com.crduels.domain.model.Apuesta;
import com.crduels.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ApuestaMapper {

    public Apuesta toEntity(ApuestaRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Apuesta apuesta = new Apuesta();
        if (dto.getJugador1Id() != null) {
            Usuario jugador1 = new Usuario();
            jugador1.setId(dto.getJugador1Id());
            apuesta.setJugador1(jugador1);
        }
        if (dto.getJugador2Id() != null) {
            Usuario jugador2 = new Usuario();
            jugador2.setId(dto.getJugador2Id());
            apuesta.setJugador2(jugador2);
        }
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
