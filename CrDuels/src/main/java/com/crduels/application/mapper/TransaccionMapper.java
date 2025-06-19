package com.crduels.application.mapper;

import com.crduels.application.dto.TransaccionRequestDto;
import com.crduels.application.dto.TransaccionResponseDto;
import com.crduels.domain.model.Transaccion;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransaccionMapper {

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "estado", ignore = true)
    @org.mapstruct.Mapping(target = "creadoEn", ignore = true)
    Transaccion toEntity(TransaccionRequestDto dto);

    TransaccionResponseDto toDto(Transaccion entity);
}
