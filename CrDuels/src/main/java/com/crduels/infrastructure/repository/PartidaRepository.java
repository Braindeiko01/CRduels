package com.crduels.infrastructure.repository;

import com.crduels.domain.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PartidaRepository extends JpaRepository<Partida, UUID> {
    Optional<Partida> findByApuesta_Id(UUID apuestaId);

    @Query("SELECT p FROM Partida p WHERE p.validada = false AND (p.apuesta.jugador1.telefono = :tel OR p.apuesta.jugador2.telefono = :tel)")
    Optional<Partida> findActivaPorTelefono(@Param("tel") String telefono);

    /**
     * Busca una partida activa donde el usuario pueda estar registrado como jugador1 o jugador2.
     *
     * @param jugador1 waId del jugador 1
     * @param jugador2 waId del jugador 2
     * @return partida activa si existe
     */
    Optional<Partida> findByActivaTrueAndJugador1WaIdOrJugador2WaId(String jugador1, String jugador2);
}
