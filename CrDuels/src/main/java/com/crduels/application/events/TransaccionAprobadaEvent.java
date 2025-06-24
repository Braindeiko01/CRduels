package com.crduels.application.events;

import com.crduels.infrastructure.dto.rs.TransaccionResponse;

public record TransaccionAprobadaEvent(TransaccionResponse transaccion) {

}
