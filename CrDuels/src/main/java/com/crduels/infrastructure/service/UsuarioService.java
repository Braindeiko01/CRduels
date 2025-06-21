package com.crduels.infrastructure.service;

import com.crduels.infrastructure.dto.UsuarioDto;
import com.crduels.infrastructure.exception.DuplicateUserException;
import com.crduels.infrastructure.mapper.UsuarioMapper;
import com.crduels.domain.entity.Usuario;
import com.crduels.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

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
