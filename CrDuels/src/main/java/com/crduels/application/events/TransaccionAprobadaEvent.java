package com.crduels.application.events;

import com.crduels.infrastructure.dto.rs.TransaccionResponse;

public class TransaccionAprobadaEvent {
    private final TransaccionResponse transaccion;

    public TransaccionAprobadaEvent(TransaccionResponse transaccion) {
        this.transaccion = transaccion;
    }

    public TransaccionResponse getTransaccion() {
        return transaccion;
    }
}
