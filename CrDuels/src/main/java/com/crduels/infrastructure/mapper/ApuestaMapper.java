package com.crduels.infrastructure.mapper;

import com.crduels.infrastructure.dto.ApuestaRequestDto;
import com.crduels.infrastructure.dto.ApuestaResponseDto;
import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ApuestaMapper {

    public Apuesta toEntity(ApuestaRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Apuesta.ApuestaBuilder builder = Apuesta.builder()
                .monto(dto.getMonto())
                .modoJuego(dto.getModoJuego());

        if (dto.getJugador1Id() != null) {
            Usuario jugador1 = Usuario.builder().id(dto.getJugador1Id()).build();
            builder.jugador1(jugador1);
        }
        if (dto.getJugador2Id() != null) {
            Usuario jugador2 = Usuario.builder().id(dto.getJugador2Id()).build();
            builder.jugador2(jugador2);
        }

        return builder.build();
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
