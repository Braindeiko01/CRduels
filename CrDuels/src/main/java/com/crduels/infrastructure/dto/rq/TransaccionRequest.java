package com.crduels.infrastructure.dto.rq;

import com.crduels.domain.entity.TipoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionRequest {
    private String usuarioId;
    private BigDecimal monto;
    private TipoTransaccion tipo;
}
