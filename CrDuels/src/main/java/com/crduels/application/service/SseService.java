package com.crduels.application.service;

import com.crduels.infrastructure.dto.rs.TransaccionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class SseService {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitters.add(emitter);
        return emitter;
    }

    public void enviarTransaccionAprobada(TransaccionResponse dto) {
        List<SseEmitter> muertos = new ArrayList<>();
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("transaccion-aprobada")
                        .data(dto));
            } catch (IOException e) {
                muertos.add(emitter);
            }
        }
        emitters.removeAll(muertos);
    }
}
