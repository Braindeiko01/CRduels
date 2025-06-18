package com.crduels.application.mapper;

import com.crduels.application.dto.UsuarioDto;
import com.crduels.domain.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioDto toDto(Usuario usuario);
    Usuario toEntity(UsuarioDto dto);
}
