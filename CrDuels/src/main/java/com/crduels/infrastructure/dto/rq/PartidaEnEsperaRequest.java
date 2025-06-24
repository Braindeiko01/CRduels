package com.crduels.infrastructure.dto.rq;

import com.crduels.domain.entity.partida.ModoJuego;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PartidaEnEsperaRequest {
    @NotEmpty
    private String jugadorId;

    @NotEmpty
    //private List<ModoJuego> modosJuego;//todo: volver a lista
    private ModoJuego modosJuego;

    @NotNull
    private BigDecimal monto;
}
