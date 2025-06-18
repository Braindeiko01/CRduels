package com.crduels.application.mapper;

import com.crduels.domain.model.Usuario;
import com.crduels.application.dto.UsuarioDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDto toDto(Usuario usuario);
    Usuario toEntity(UsuarioDto dto);
}
