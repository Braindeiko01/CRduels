package com.crduels.application.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * DTO para la creación de apuestas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApuestaRequestDto {
    private Long jugador1Id;
    private Long jugador2Id;
    private BigDecimal monto;
    private String modoJuego;
}
