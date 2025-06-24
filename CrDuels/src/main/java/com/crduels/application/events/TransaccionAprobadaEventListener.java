package com.crduels.application.events;

import com.crduels.application.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransaccionAprobadaEventListener {

    private final SseService sseService;

    @EventListener
    public void handleTransaccionAprobada(TransaccionAprobadaEvent event) {
        sseService.notificarTransaccionAprobada(event.transaccion());
    }
}
