package com.crduels.application.mapper;

import com.crduels.application.dto.ApuestaRequestDto;
import com.crduels.application.dto.ApuestaResponseDto;
import com.crduels.domain.model.Apuesta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApuestaMapper {
    Apuesta toEntity(ApuestaRequestDto dto);
    ApuestaResponseDto toDto(Apuesta entity);
}
