package com.crduels.infrastructure.mapper;

import com.crduels.domain.entity.Jugador;
import com.crduels.domain.entity.partida.PartidaEnEspera;
import com.crduels.infrastructure.dto.rq.PartidaEnEsperaRequest;

public final class PartidaEnEsperaMapper {

    private PartidaEnEsperaMapper() {
    }

    public static PartidaEnEspera toEntity(PartidaEnEsperaRequest request) {
        return PartidaEnEspera.builder()
                .jugador(new Jugador(request.getJugadorId()))
                .modosJuego(request.getModosJuego())
                .monto(request.getMonto())
                .build();
    }


}
