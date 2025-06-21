package com.crduels.application.service;

import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.EstadoApuesta;
import com.crduels.domain.entity.TipoTransaccion;
import com.crduels.domain.entity.Usuario;
import com.crduels.infrastructure.dto.rq.ApuestaRequest;
import com.crduels.infrastructure.dto.rq.TransaccionRequest;
import com.crduels.infrastructure.dto.rs.ApuestaResponse;
import com.crduels.infrastructure.dto.rs.TransaccionResponse;
import com.crduels.infrastructure.mapper.ApuestaMapper;
import com.crduels.infrastructure.repository.ApuestaRepository;
import com.crduels.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApuestaService {

    private final ApuestaRepository apuestaRepository;
    private final ApuestaMapper apuestaMapper;
    private final UsuarioRepository usuarioRepository;
    private final TransaccionService transaccionService;

    @Transactional
    public ApuestaResponse crearApuesta(ApuestaRequest dto) {
        Apuesta apuesta = apuestaMapper.toEntity(dto);
        apuesta.setEstado(EstadoApuesta.PENDIENTE);
        apuesta.setCreadoEn(LocalDateTime.now());

        Usuario jugador1 = usuarioRepository.findById(apuesta.getJugador1().getId())
                .orElseThrow(() -> new IllegalArgumentException("Jugador 1 no encontrado"));
        Usuario jugador2 = usuarioRepository.findById(apuesta.getJugador2().getId())
                .orElseThrow(() -> new IllegalArgumentException("Jugador 2 no encontrado"));


        //todo: validar si es suficiente la validacion de transaccion
        if (jugador1.getSaldo().compareTo(apuesta.getMonto()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para el jugador 1");
        }
        if (jugador2.getSaldo().compareTo(apuesta.getMonto()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para el jugador 2");
        }

        realizarTransacciones(apuesta, jugador1, jugador2);

        Apuesta saved = apuestaRepository.save(apuesta);
        return apuestaMapper.toDto(saved);
    }

    private void realizarTransacciones(Apuesta apuesta, Usuario jugador1, Usuario jugador2) {
        TransaccionRequest transaccionRqJugador1 = TransaccionRequest.builder()
                .monto(apuesta.getMonto())
                .usuarioId(jugador1.getId())
                .tipo(TipoTransaccion.APUESTA)
                .build();
        TransaccionRequest transaccionRqJugador2 = TransaccionRequest.builder()
                .monto(apuesta.getMonto())
                .usuarioId(jugador2.getId())
                .tipo(TipoTransaccion.APUESTA)
                .build();

        TransaccionResponse transaccionRsJugador1 = transaccionService.registrarTransaccion(transaccionRqJugador1);
        TransaccionResponse transaccionRsJugador2 = transaccionService.registrarTransaccion(transaccionRqJugador2);

        transaccionService.aprobarTransaccion(transaccionRsJugador1.getId());
        transaccionService.aprobarTransaccion(transaccionRsJugador2.getId());
    }

    public List<ApuestaResponse> listarPendientesPorModo(String modoJuego) {
        return apuestaRepository.findByEstado(EstadoApuesta.PENDIENTE).stream()
                .filter(a -> a.getModoJuego().equalsIgnoreCase(modoJuego))
                .map(apuestaMapper::toDto)
                .collect(Collectors.toList());
    }

    public ApuestaResponse cambiarEstado(UUID id, EstadoApuesta estado) {
        Apuesta apuesta = apuestaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Apuesta no encontrada"));
        apuesta.setEstado(estado);
        Apuesta saved = apuestaRepository.save(apuesta);
        return apuestaMapper.toDto(saved);
    }
}
