package com.crduels.application.mapper;

import com.crduels.domain.model.Usuario;
import com.crduels.application.dto.UsuarioDto;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return UsuarioDto.builder()
                .googleId(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .tagClash(usuario.getTagClash())
                .linkAmistad(usuario.getLinkAmistad())
                .saldo(usuario.getSaldo())
                .reputacion(usuario.getReputacion())
                .build();
    }

    public Usuario toEntity(UsuarioDto dto) {
        if (dto == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(dto.getGoogleId());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setTagClash(dto.getTagClash());
        usuario.setLinkAmistad(dto.getLinkAmistad());
        // saldo and reputacion are managed by the entity defaults
        return usuario;
    }

}
