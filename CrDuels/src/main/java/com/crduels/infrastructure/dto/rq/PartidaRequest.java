package com.crduels.infrastructure.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidaRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -6246739242263766423L;

    private UUID apuestaId;
    private String ganadorId;
    private String resultadoJson;
}
