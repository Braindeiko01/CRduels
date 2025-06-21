package com.crduels.application.dto;

import com.crduels.domain.model.EstadoTransaccion;
import com.crduels.domain.model.TipoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionResponseDto {
    private UUID id;
    private String usuarioId;
    private BigDecimal monto;
    private TipoTransaccion tipo;
    private EstadoTransaccion estado;
    private LocalDateTime creadoEn;
}
