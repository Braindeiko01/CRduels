package com.crduels.application.dto;

import lombok.*;

import java.util.UUID;

/**
 * DTO para la creación de partidas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidaRequestDto {
    private UUID apuestaId;
    private Long ganadorId;
    private String resultadoJson;
}
