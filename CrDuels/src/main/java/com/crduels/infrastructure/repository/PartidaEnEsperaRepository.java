package com.crduels.infrastructure.repository;

import com.crduels.domain.entity.Jugador;
import com.crduels.domain.entity.partida.ModoJuego;
import com.crduels.domain.entity.partida.PartidaEnEspera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface PartidaEnEsperaRepository extends JpaRepository<PartidaEnEspera, UUID> {

    @Query("SELECT p FROM PartidaEnEspera p " +
            "WHERE CONCAT(',', p.modosJuego, ',') LIKE CONCAT('%,', :#{#modoJuego.name()}, ',%') " +
            "AND p.monto = :monto")
    List<PartidaEnEspera> findByModoJuegoAndMonto(@Param("modoJuego") ModoJuego modoJuego,
                                                  @Param("monto") BigDecimal monto);

    void deleteByJugador(Jugador jugador);

}
