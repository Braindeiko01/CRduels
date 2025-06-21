package com.crduels.domain.matchmaking;

import com.crduels.domain.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa una apuesta temporal utilizada
 * durante el proceso de matchmaking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Apuesta {

    /** Monto fijo de la apuesta en pesos colombianos. */
    public static final BigDecimal MONTO_FIJO = BigDecimal.valueOf(6000);

    private Usuario jugador1;
    private Usuario jugador2;
    @Builder.Default
    private BigDecimal monto = MONTO_FIJO;
    private EstadoApuesta estado;
    private ModoJuego modoJuego;
    @Builder.Default
    private LocalDateTime creadaEn = LocalDateTime.now();
}
