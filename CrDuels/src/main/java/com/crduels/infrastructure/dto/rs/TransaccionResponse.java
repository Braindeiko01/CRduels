package com.crduels.infrastructure.dto.rs;

import com.crduels.domain.entity.EstadoTransaccion;
import com.crduels.domain.entity.TipoTransaccion;
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
public class TransaccionResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -1358964053489023581L;
    
    private UUID id;
    private String usuarioId;
    private BigDecimal monto;
    private TipoTransaccion tipo;
    private EstadoTransaccion estado;
    private LocalDateTime creadoEn;
}
