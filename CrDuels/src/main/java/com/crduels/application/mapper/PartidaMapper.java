package com.crduels.application.mapper;

import com.crduels.application.dto.PartidaRequestDto;
import com.crduels.application.dto.PartidaResponseDto;
import com.crduels.domain.model.Partida;
import com.crduels.domain.model.Apuesta;
import com.crduels.domain.model.Usuario;

import org.springframework.stereotype.Component;

@Component
public class PartidaMapper {

    public Partida toEntity(PartidaRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Partida partida = new Partida();
        if (dto.getApuestaId() != null) {
            Apuesta apuesta = new Apuesta();
            apuesta.setId(dto.getApuestaId());
            partida.setApuesta(apuesta);
        }
        if (dto.getGanadorId() != null) {
            Usuario ganador = new Usuario();
            ganador.setId(dto.getGanadorId());
            partida.setGanador(ganador);
        }
        partida.setResultadoJson(dto.getResultadoJson());
        return partida;
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