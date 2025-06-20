package com.crduels.application.service;

import com.crduels.application.dto.UsuarioDto;
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

        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(saved);
    }

    public Optional<UsuarioDto> obtenerPorId(String id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDto);
    }

}
