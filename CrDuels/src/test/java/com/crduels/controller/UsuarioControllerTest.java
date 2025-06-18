package com.crduels.controller;

import com.crduels.entity.Usuario;
import com.crduels.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void registrar_devuelveUsuarioRegistrado() throws Exception {
        Usuario usuario = crearUsuario();
        Mockito.when(usuarioService.registrarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(usuario.getEmail()));
    }

    @Test
    void obtener_devuelveUsuarioCuandoExiste() throws Exception {
        Usuario usuario = crearUsuario();
        UUID id = usuario.getId();
        Mockito.when(usuarioService.obtenerPorId(id)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void obtener_devuelveNotFoundCuandoNoExiste() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(usuarioService.obtenerPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/" + id))
                .andExpect(status().isNotFound());
    }

    private Usuario crearUsuario() {
        Usuario u = new Usuario();
        u.setNombre("Test");
        u.setEmail("test@example.com");
        u.setTelefono("+123456789");
        u.setTagClash("#TEST1");
        return u;
    }
}
