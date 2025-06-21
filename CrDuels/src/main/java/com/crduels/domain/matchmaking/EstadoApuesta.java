package com.crduels.domain.matchmaking;

/**
 * Estados posibles de una apuesta dentro del sistema de matchmaking.
 */
public enum EstadoApuesta {
    /**
     * Apuesta esperando por un oponente.
     */
    PENDIENTE,
    /**
     * Apuesta con ambos jugadores asignados lista para jugar.
     */
    ACTIVA
}
