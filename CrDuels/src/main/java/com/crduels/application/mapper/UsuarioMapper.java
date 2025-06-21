package com.crduels.application.mapper;

import com.crduels.application.dto.UsuarioDto;
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

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setTagClash(extractTagFromUrl(dto.getEmail()));
        usuario.setLinkAmistad(dto.getLinkAmistad());

        return usuario;
    }

    public String extractTagFromUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }

        Matcher matcher = TAG_PATTERN.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }


}
