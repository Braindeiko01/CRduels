package com.crduels.application.mapper;

import com.crduels.application.dto.PartidaRequestDto;
import com.crduels.application.dto.PartidaResponseDto;
import com.crduels.domain.model.Partida;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PartidaMapper {

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "validada", ignore = true)
    @org.mapstruct.Mapping(target = "validadaEn", ignore = true)
    Partida toEntity(PartidaRequestDto dto);

    PartidaResponseDto toDto(Partida entity);
}
