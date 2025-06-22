package com.crduels.infrastructure.dto.rs;

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
public class MatchmakingPartidaDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID partidaId;
    private String jugador1Id;
    private String jugador2Id;
    private String modoJuego;
}
