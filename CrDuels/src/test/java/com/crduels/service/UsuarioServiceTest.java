package com.crduels.service;

import com.crduels.entity.Usuario;
import com.crduels.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_guardaYDevuelveUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.registrarUsuario(usuario);

        assertSame(usuario, resultado);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void obtenerPorId_devuelveUsuarioCuandoExiste() {
        UUID id = UUID.randomUUID();
        Usuario usuario = new Usuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.obtenerPorId(id);

        assertTrue(resultado.isPresent());
        assertSame(usuario, resultado.get());
        verify(usuarioRepository).findById(id);
    }

    @Test
    void obtenerPorId_devuelveVacioCuandoNoExiste() {
        UUID id = UUID.randomUUID();
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.obtenerPorId(id);

        assertTrue(resultado.isEmpty());
        verify(usuarioRepository).findById(id);
    }
}
