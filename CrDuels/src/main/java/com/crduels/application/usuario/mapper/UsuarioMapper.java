package com.crduels.application.usuario.mapper;

import com.crduels.application.usuario.dto.UsuarioDto;
import com.crduels.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UsuarioMapper {

    private static final Pattern TAG_PATTERN = Pattern.compile("tag=([^&]+)");

    public UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .linkAmistad(usuario.getLinkAmistad())
                .build();
    }

    public Usuario toEntity(UsuarioDto dto) {
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
