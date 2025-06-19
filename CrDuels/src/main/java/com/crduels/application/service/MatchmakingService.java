package com.crduels.application.service;

import com.crduels.application.dto.MatchResultDto;
import com.crduels.domain.model.Apuesta;
import com.crduels.domain.model.EstadoApuesta;
import com.crduels.infrastructure.repository.ApuestaRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchmakingService {

    private final ApuestaRepository apuestaRepository;

    public MatchmakingService(ApuestaRepository apuestaRepository) {
        this.apuestaRepository = apuestaRepository;
    }

    /**
     * Ejecuta el proceso de emparejamiento de apuestas. Se buscan todas las
     * apuestas con estado PENDIENTE y se agrupan por monto y modo de juego.
     * Para cada grupo se toman pares de apuestas y se marcan como EMPAREJADA.
     */
    public List<MatchResultDto> ejecutarMatchmaking() {
        // 1. Obtener todas las apuestas pendientes
        List<Apuesta> pendientes = apuestaRepository.findByEstado(EstadoApuesta.PENDIENTE);

        // 2. Agrupar por monto y modo de juego
        Map<Key, List<Apuesta>> grupos = pendientes.stream()
                .collect(Collectors.groupingBy(a -> new Key(a.getMonto(), a.getModoJuego())));

        // 3. Procesar cada grupo buscando pares
        List<MatchResultDto> resultados = new ArrayList<>();
        for (List<Apuesta> grupo : grupos.values()) {
            for (int i = 0; i + 1 < grupo.size(); i += 2) {
                Apuesta a1 = grupo.get(i);
                Apuesta a2 = grupo.get(i + 1);

                // 4. Cambiar estado y guardar
                a1.setEstado(EstadoApuesta.EMPAREJADA);
                a2.setEstado(EstadoApuesta.EMPAREJADA);
                apuestaRepository.save(a1);
                apuestaRepository.save(a2);

                resultados.add(new MatchResultDto(a1.getId(), a2.getId(), a1.getMonto(), a1.getModoJuego()));
            }
        }

        return resultados;
    }

    /**
     * Llave para el agrupamiento por monto y modo de juego.
     */
    private record Key(java.math.BigDecimal monto, String modo) { }
}
