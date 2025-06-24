package com.crduels.infrastructure.mapper;

import com.crduels.domain.entity.Jugador;
import com.crduels.domain.entity.partida.ModoJuego;
import com.crduels.domain.entity.partida.PartidaEnEspera;
import com.crduels.infrastructure.dto.rq.PartidaEnEsperaRequest;

import java.util.ArrayList;
import java.util.List;

public final class PartidaEnEsperaMapper {

    private PartidaEnEsperaMapper() {
    }

    public static PartidaEnEspera toEntity(PartidaEnEsperaRequest request) {
        return PartidaEnEspera.builder()
                .jugador(new Jugador(request.getJugadorId()))
                .modoJuego(request.getModoJuego())
                .monto(request.getMonto())
                .build();
    }


}
