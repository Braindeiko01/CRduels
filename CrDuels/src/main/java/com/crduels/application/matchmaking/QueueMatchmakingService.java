package com.crduels.application.matchmaking;

import com.crduels.domain.entity.Usuario;
import com.crduels.domain.matchmaking.Apuesta;
import com.crduels.domain.matchmaking.EstadoApuesta;
import com.crduels.domain.matchmaking.ModoJuego;
import com.crduels.application.matchmaking.MatchmakingQueue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Servicio responsable de gestionar el emparejamiento de apuestas en
 * memoria utilizando colas FIFO. Se mantiene separado del servicio de
 * {@code application.service.MatchmakingService} que opera sobre la base
 * de datos.
 */
@Service
@RequiredArgsConstructor
public class QueueMatchmakingService {

    private final MatchmakingQueue matchmakingQueue;

    /**
     * Recibe la solicitud de un jugador para crear una apuesta en cierto modo
     * de juego. Si existe otra apuesta pendiente en la misma cola se emparejan
     * y se retorna la apuesta resultante. En caso contrario la nueva apuesta
     * se almacena como pendiente.
     *
     * @param jugador  jugador que desea apostar
     * @param modoJuego modo de juego elegido
     * @return la apuesta emparejada o la apuesta pendiente
     */
    public synchronized Apuesta registrarApuesta(Usuario jugador, ModoJuego modoJuego) {
        Objects.requireNonNull(jugador, "jugador1 no puede ser null");
        Objects.requireNonNull(modoJuego, "modoJuego no puede ser null");

        ConcurrentLinkedQueue<Apuesta> cola = matchmakingQueue.obtenerCola(modoJuego);
        Apuesta pendiente = cola.poll();
        if (pendiente != null) {
            pendiente.setJugador2(jugador);
            pendiente.setEstado(EstadoApuesta.ACTIVA);
            return pendiente;
        }

        Apuesta nueva = Apuesta.builder()
                .jugador1(jugador)
                .estado(EstadoApuesta.PENDIENTE)
                .modoJuego(modoJuego)
                .build();
        matchmakingQueue.encolar(modoJuego, nueva);
        return nueva;
    }
}
