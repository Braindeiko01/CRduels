package com.crduels.infrastructure.dto.rq;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApuestaRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2077072453093575730L;

    @NotEmpty
    private String jugador1Id;

    @NotEmpty
    private String jugador2Id;

    private BigDecimal monto;
    private String modoJuego;
}
