package com.crduels.infrastructure.dto.rq;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchmakingJoinRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String usuarioId;

    @NotEmpty
    private String modoJuego;
}
