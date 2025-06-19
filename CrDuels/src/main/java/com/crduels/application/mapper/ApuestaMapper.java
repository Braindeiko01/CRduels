package com.crduels.application.mapper;

import com.crduels.application.dto.ApuestaRequestDto;
import com.crduels.application.dto.ApuestaResponseDto;
import com.crduels.domain.model.Apuesta;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ApuestaMapper {

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "estado", ignore = true)
    @org.mapstruct.Mapping(target = "creadoEn", ignore = true)
    Apuesta toEntity(ApuestaRequestDto dto);

    ApuestaResponseDto toDto(Apuesta entity);
}
