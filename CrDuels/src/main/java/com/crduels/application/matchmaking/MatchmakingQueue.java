package com.crduels.application.matchmaking;

import com.crduels.domain.matchmaking.Apuesta;
import com.crduels.domain.matchmaking.ModoJuego;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Maneja las colas de emparejamiento por cada modo de juego.
 */
@Component
public class MatchmakingQueue {

    private final Map<ModoJuego, ConcurrentLinkedQueue<Apuesta>> colas;

    public MatchmakingQueue() {
        this.colas = new EnumMap<>(ModoJuego.class);
        for (ModoJuego modo : ModoJuego.values()) {
            this.colas.put(modo, new ConcurrentLinkedQueue<>());
        }
    }

    public ConcurrentLinkedQueue<Apuesta> obtenerCola(ModoJuego modo) {
        return colas.get(modo);
    }

    public void encolar(ModoJuego modo, Apuesta apuesta) {
        obtenerCola(modo).offer(apuesta);
    }

    public Apuesta desencolar(ModoJuego modo) {
        return obtenerCola(modo).poll();
    }
}
