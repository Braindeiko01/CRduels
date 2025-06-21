package com.crduels.application.apuesta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para la creación de apuestas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApuestaRequestDto {
    private String jugador1Id;
    private String jugador2Id;
    private BigDecimal monto;
    private String modoJuego;
}
