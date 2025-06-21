package com.crduels.application.partida.mapper;

import com.crduels.application.partida.dto.PartidaRequestDto;
import com.crduels.application.partida.dto.PartidaResponseDto;
import com.crduels.domain.model.Apuesta;
import com.crduels.domain.model.Partida;
import com.crduels.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class PartidaMapper {

    public Partida toEntity(PartidaRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Partida.PartidaBuilder builder = Partida.builder()
                .resultadoJson(dto.getResultadoJson());
        if (dto.getApuestaId() != null) {
            Apuesta apuesta = Apuesta.builder().id(dto.getApuestaId()).build();
            builder.apuesta(apuesta);
        }
        if (dto.getGanadorId() != null) {
            Usuario ganador = Usuario.builder().id(dto.getGanadorId()).build();
            builder.ganador(ganador);
        }
        return builder.build();
    }

    public PartidaResponseDto toDto(Partida entity) {
        if (entity == null) {
            return null;
        }
        return PartidaResponseDto.builder()
                .id(entity.getId())
                .apuestaId(entity.getApuesta() != null ? entity.getApuesta().getId() : null)
                .ganadorId(entity.getGanador() != null ? entity.getGanador().getId() : null)
                .validada(entity.isValidada())
                .validadaEn(entity.getValidadaEn())
                .build();
    }
}