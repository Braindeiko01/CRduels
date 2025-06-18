package com.crduels.service;

import com.crduels.entity.Usuario;
import com.crduels.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya est\u00e1 registrado");
        }
        if (usuarioRepository.existsByTelefono(usuario.getTelefono())) {
            throw new IllegalArgumentException("El tel\u00e9fono ya est\u00e1 registrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obtenerPorId(UUID id) {
        return usuarioRepository.findById(id);
    }
}
