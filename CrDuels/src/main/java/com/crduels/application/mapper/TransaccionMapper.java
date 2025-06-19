package com.crduels.application.mapper;

import com.crduels.application.dto.TransaccionRequestDto;
import com.crduels.application.dto.TransaccionResponseDto;
import com.crduels.domain.model.Transaccion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransaccionMapper {
    Transaccion toEntity(TransaccionRequestDto dto);
    TransaccionResponseDto toDto(Transaccion entity);
}
