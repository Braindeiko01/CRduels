package com.crduels.application.service;

import com.crduels.domain.entity.Usuario;
import com.crduels.infrastructure.dto.rq.UsuarioRequest;
import com.crduels.infrastructure.dto.rs.UsuarioResponse;
import com.crduels.infrastructure.exception.DuplicateUserException;
import com.crduels.infrastructure.mapper.UsuarioMapper;
import com.crduels.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioResponse registrarUsuario(UsuarioRequest dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateUserException("El email ya está registrado");
        }

        if (usuarioRepository.existsByTelefono(dto.getTelefono())) {
            throw new DuplicateUserException("El teléfono ya está registrado");
        }
        // Mapeo de DTO A entidad
        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(saved);
    }

    public Optional<UsuarioResponse> obtenerPorId(String id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDto);
    }

}
