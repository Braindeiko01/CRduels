package com.crduels.infrastructure.dto.rs;

import com.crduels.domain.entity.EstadoApuesta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApuestaResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -3599738233275444976L;

    private UUID id;
    private BigDecimal monto;
    private String modoJuego;
    private EstadoApuesta estado;
    private LocalDateTime creadoEn;
}
