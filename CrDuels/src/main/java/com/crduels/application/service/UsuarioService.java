package com.crduels.application.service;

import com.crduels.application.dto.UsuarioDto;
import com.crduels.application.dto.GoogleUserData;
import com.crduels.application.mapper.UsuarioMapper;
import com.crduels.application.exception.DuplicateUserException;
import com.crduels.domain.model.Usuario;
import com.crduels.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDto registrarUsuario(UsuarioDto dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateUserException("El email ya está registrado");
        }

        if (usuarioRepository.existsByTelefono(dto.getTelefono())) {
            throw new DuplicateUserException("El teléfono ya está registrado");
        }

        // sanitize the Clash Royale tag before persisting
        String sanitizedTag = sanitizeTag(dto.getTagClash());
        dto.setTagClash(sanitizedTag);

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setTagClash(sanitizedTag);
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(saved);
    }

    /**
     * Registra un usuario a partir de los datos proporcionados por Google.
     * Si ya existe un usuario con el mismo identificador se devuelve tal cual
     * sin crear uno nuevo.
     */
    public Usuario registrarUsuario(GoogleUserData googleData) {
        return usuarioRepository.findByGoogleId(googleData.getGoogleId())
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setGoogleId(googleData.getGoogleId());
                    nuevo.setNombre(googleData.getNombre());
                    nuevo.setEmail(googleData.getEmail());
                    // Campo imagen no se almacena actualmente en la entidad
                    return usuarioRepository.save(nuevo);
                });
    }

    public Optional<UsuarioDto> obtenerPorId(Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDto);
    }

    /**
     * Remove a leading '#' from the tag and convert it to upper case.
     */
    private String sanitizeTag(String tag) {
        if (tag == null) {
            return null;
        }
        tag = tag.trim();
        if (tag.startsWith("#")) {
            tag = tag.substring(1);
        }
        return tag.toUpperCase();
    }

}
