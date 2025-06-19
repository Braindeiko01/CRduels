package com.crduels.application.mapper;

import com.crduels.domain.model.Usuario;
import com.crduels.application.dto.UsuarioDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UsuarioMapper {

    UsuarioDto toDto(Usuario usuario);

    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "saldo", ignore = true)
    @org.mapstruct.Mapping(target = "reputacion", ignore = true)
    Usuario toEntity(UsuarioDto dto);
}
