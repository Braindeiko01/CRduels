package com.crduels.application.mapper;

import com.crduels.application.dto.PartidaRequestDto;
import com.crduels.application.dto.PartidaResponseDto;
import com.crduels.domain.model.Partida;

import org.springframework.stereotype.Component;

@Component
public class PartidaMapper {

    public Partida toEntity(PartidaRequestDto dto) {
        if (dto == null) {
            return null;
        }
        Partida partida = new Partida();
        partida.setApuestaId(dto.getApuestaId());
        partida.setGanadorId(dto.getGanadorId());
        partida.setResultadoJson(dto.getResultadoJson());
        return partida;
    }

    public PartidaResponseDto toDto(Partida entity) {
        if (entity == null) {
            return null;
        }
        return PartidaResponseDto.builder()
                .id(entity.getId())
                .apuestaId(entity.getApuestaId())
                .ganadorId(entity.getGanadorId())
                .validada(entity.isValidada())
                .validadaEn(entity.getValidadaEn())
                .build();
    }
}