package com.crduels.infrastructure.dto.rq;

import com.crduels.domain.entity.partida.ModoJuego;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PartidaEnEsperaRequest {
    @NotEmpty
    private String jugadorId;

    @NotEmpty
    private ModoJuego modoJuego;

    @NotNull
    private BigDecimal monto;
}
