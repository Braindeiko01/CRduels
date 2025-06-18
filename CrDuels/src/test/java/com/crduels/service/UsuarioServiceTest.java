package com.crduels.service;

import com.crduels.application.dto.UsuarioDto;
import com.crduels.application.service.UsuarioService;
import com.crduels.infrastructure.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(UsuarioService.class)
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioDto crearUsuario(String email, String telefono) {
        UsuarioDto usuario = new UsuarioDto();
        usuario.setNombre("Test");
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        String tag = UUID.randomUUID().toString().replace("-", "").substring(0,8).toUpperCase();
        usuario.setTagClash("#" + tag);
        return usuario;
    }

    @Test
    void registrarUsuario_guardaUsuario() {
        UsuarioDto usuario = crearUsuario("test1@example.com", "+111111111");
        UsuarioDto guardado = usuarioService.registrarUsuario(usuario);
        usuarioRepository.flush();
        assertTrue(usuarioRepository.findById(guardado.getId()).isPresent());
    }

    @Test
    void registrarUsuario_emailDuplicado_rechazado() {
        UsuarioDto primero = crearUsuario("dup@example.com", "+222222222");
        usuarioService.registrarUsuario(primero);
        usuarioRepository.flush();

        UsuarioDto duplicado = crearUsuario("dup@example.com", "+333333333");
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioService.registrarUsuario(duplicado);
            usuarioRepository.flush();
        });
    }

    @Test
    void registrarUsuario_telefonoDuplicado_rechazado() {
        UsuarioDto primero = crearUsuario("phone@example.com", "+444444444");
        usuarioService.registrarUsuario(primero);
        usuarioRepository.flush();

        UsuarioDto duplicado = crearUsuario("other@example.com", "+444444444");
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioService.registrarUsuario(duplicado);
            usuarioRepository.flush();
        });
    }
}
