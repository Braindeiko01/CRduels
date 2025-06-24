package com.crduels.infrastructure.repository;

import com.crduels.domain.entity.Jugador;
import com.crduels.domain.entity.partida.ModoJuego;
import com.crduels.domain.entity.partida.PartidaEnEspera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface PartidaEnEsperaRepository extends JpaRepository<PartidaEnEspera, UUID> {

    List<PartidaEnEspera> findByModoJuegoAndMonto(ModoJuego modoJuego, BigDecimal monto);

    void deleteByJugador(Jugador jugador);

}
