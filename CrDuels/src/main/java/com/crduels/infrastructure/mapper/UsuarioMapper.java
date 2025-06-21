package com.crduels.infrastructure.mapper;

import com.crduels.infrastructure.dto.rq.UsuarioRequest;
import com.crduels.domain.entity.Usuario;
import com.crduels.infrastructure.dto.rs.UsuarioResponse;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UsuarioMapper {

    private static final Pattern TAG_PATTERN = Pattern.compile("tag=([^&]+)");

    public UsuarioResponse toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .tagClash(usuario.getTagClash())
                .linkAmistad(usuario.getLinkAmistad())
                .saldo(usuario.getSaldo())
                .reputacion(usuario.getReputacion())
                .build();
    }

    public Usuario toEntity(UsuarioRequest dto) {
        if (dto == null) {
            return null;
        }

        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .tagClash(extractTagFromUrl(dto.getLinkAmistad()))
                .linkAmistad(dto.getLinkAmistad())
                .build();
    }

    public static String extractTagFromUrl(String url) {

        Matcher matcher = TAG_PATTERN.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }


}
