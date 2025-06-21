package com.crduels.infrastructure.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO para la creación de partidas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidaRequest {
    private UUID apuestaId;
    private String ganadorId;
    private String resultadoJson;
}
