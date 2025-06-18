package com.crduels.application.mapper;

import com.crduels.application.dto.PartidaRequestDto;
import com.crduels.application.dto.PartidaResponseDto;
import com.crduels.domain.model.Partida;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartidaMapper {
    Partida toEntity(PartidaRequestDto dto);
    PartidaResponseDto toDto(Partida entity);
}
