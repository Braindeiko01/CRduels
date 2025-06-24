package com.crduels.application.service;

import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.Jugador;
import com.crduels.domain.entity.TipoTransaccion;
import com.crduels.domain.entity.partida.ModoJuego;
import com.crduels.domain.entity.partida.Partida;
import com.crduels.domain.entity.partida.PartidaEnEspera;
import com.crduels.infrastructure.dto.rq.ApuestaRequest;
import com.crduels.infrastructure.dto.rq.PartidaEnEsperaRequest;
import com.crduels.infrastructure.dto.rq.TransaccionRequest;
import com.crduels.infrastructure.dto.rs.TransaccionResponse;
import com.crduels.infrastructure.mapper.PartidaEnEsperaMapper;
import com.crduels.infrastructure.repository.JugadorRepository;
import com.crduels.infrastructure.repository.PartidaEnEsperaRepository;
import com.crduels.infrastructure.repository.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchmakingService {

    private final PartidaEnEsperaRepository partidaEnEsperaRepository;
    private final TransaccionService transaccionService;
    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private final ChatService chatService;
    private final ApuestaService apuestaService;
    private final MatchSseService matchSseService;

    private static final List<ModoJuego> PRIORIDAD_MODO_JUEGO = List.of(
            ModoJuego.TRIPLE_ELECCION,
            ModoJuego.CLASICO
    );

    @Transactional
    public Optional<Partida> intentarEmparejar(PartidaEnEsperaRequest request) {
        Jugador jugadorEnEspera = jugadorRepository.findById(request.getJugadorId())
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));

        if (jugadorEnEspera.getSaldo().compareTo(request.getMonto()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar esta operación");
        }

        cancelarSolicitudes(jugadorEnEspera);

        PartidaEnEspera partidaEnEsperaRq = PartidaEnEsperaMapper.toEntity(request);
        PartidaEnEspera partidaEnEspera = partidaEnEsperaRepository.save(partidaEnEsperaRq);

        return partidaEnEsperaRepository.findByModoJuegoAndMonto(partidaEnEspera.getModosJuego().get(0), request.getMonto())
                .stream()
                .filter(p -> !p.getJugador().getId().equals(partidaEnEspera.getJugador().getId()))
                .findFirst() //todo: aquí debería estar la lógica para emparejar el matchmaking con personas del mismo nivel
                .map(partidaEncontrada -> {

                    Jugador jugadorEncontrado = jugadorRepository.findById(partidaEncontrada.getJugador().getId())
                            .orElseThrow(() -> new IllegalArgumentException("Jugador en espera no encontrado"));

                    if (jugadorEncontrado.getSaldo().compareTo(partidaEncontrada.getMonto()) < 0) {
                        throw new IllegalArgumentException("Saldo insuficiente para realizar esta operación");
                    }

                    cancelarSolicitudes(jugadorEnEspera);
                    cancelarSolicitudes(jugadorEncontrado);

                    Partida partida = crearPartida(partidaEnEspera, partidaEncontrada);

                    realizarTransaccion(partida.getApuesta(), jugadorEnEspera);
                    realizarTransaccion(partida.getApuesta(), jugadorEncontrado);

                    matchSseService.notifyMatch(partida);
                    return partida;
                });
    }

    private void cancelarSolicitudes(Jugador jugador) {
        partidaEnEsperaRepository.deleteByJugador(jugador);
    }

    public void cancelarSolicitudes(String jugadorId) {
        partidaEnEsperaRepository.deleteByJugador(new Jugador(jugadorId));
    }

    private Partida crearPartida(PartidaEnEspera partidaEnEspera, PartidaEnEspera partidaEncontrada) {
        Apuesta apuesta = apuestaService.crearApuesta(new ApuestaRequest(partidaEnEspera.getMonto()));
        ModoJuego modoJuego = obtenerModoJuegoCoincidente(partidaEnEspera, partidaEncontrada);
        UUID chatId = chatService.crearChatParaPartida(partidaEnEspera.getJugador().getId(), partidaEncontrada.getJugador().getId());

        Partida partida = Partida.builder()
                .jugador1(partidaEnEspera.getJugador())
                .jugador2(partidaEncontrada.getJugador())
                .modoJuego(modoJuego)
                .estado(com.crduels.domain.entity.partida.EstadoPartida.EN_CURSO)
                .creada(LocalDateTime.now())
                .validada(false)
                .chatId(chatId)
                .apuesta(apuesta)
                .build();

        return partidaRepository.save(partida);
    }


    private void realizarTransaccion(Apuesta apuesta, Jugador jugador) {
        TransaccionRequest request = TransaccionRequest.builder()
                .monto(apuesta.getMonto())
                .jugadorId(jugador.getId())
                .tipo(TipoTransaccion.APUESTA)
                .build();
        TransaccionResponse response = transaccionService.registrarTransaccion(request);
        transaccionService.aprobarTransaccion(response.getId());
    }


    public ModoJuego obtenerModoJuegoCoincidente(PartidaEnEspera partidaEncontrada,
                                                 PartidaEnEspera partidaSolicitada) {
        return PRIORIDAD_MODO_JUEGO.stream()
                .filter(partidaEncontrada.getModosJuego()::contains)
                .filter(partidaSolicitada.getModosJuego()::contains)
                .findFirst()
                .orElseGet(() -> PRIORIDAD_MODO_JUEGO.get(0));
    }
}
