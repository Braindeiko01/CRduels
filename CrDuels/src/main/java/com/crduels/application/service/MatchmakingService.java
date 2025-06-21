package com.crduels.application.service;

import com.crduels.infrastructure.dto.MatchResultDto;
import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.EstadoApuesta;
import com.crduels.infrastructure.repository.ApuestaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchmakingService {

    private final ApuestaRepository apuestaRepository;

    /**
     * Ejecuta el proceso de matchmaking para las apuestas pendientes.
     * <p>
     * El algoritmo realiza las siguientes acciones:
     * <ol>
     *     <li>Consulta en {@link ApuestaRepository} todas las apuestas con
     *     estado {@link EstadoApuesta#PENDIENTE}.</li>
     *     <li>Agrupa dichas apuestas usando la clase interna {@link Key} de
     *     acuerdo al mismo monto y modo de juego.</li>
     *     <li>Dentro de cada grupo forma pares tomando las apuestas de dos en
     *     dos.</li>
     *     <li>Cada par se marca con el estado
     *     {@link EstadoApuesta#EMPAREJADA} y se persiste.</li>
     *     <li>Por cada par creado se genera un {@link MatchResultDto} con la
     *     información relevante.</li>
     * </ol>
     *
     * @return lista de resultados con los emparejamientos realizados, donde cada
     * entrada representa dos apuestas compatibles.
     */
    public List<MatchResultDto> ejecutarMatchmaking() {
        List<MatchResultDto> resultados = new ArrayList<>();

        // 1. Obtener todas las apuestas pendientes
        List<Apuesta> pendientes = apuestaRepository.findByEstado(EstadoApuesta.PENDIENTE);

        // 2. Agrupar por monto y modo de juego
        Map<Key, List<Apuesta>> grupos = pendientes.stream()
                .collect(Collectors.groupingBy(a -> new Key(a.getMonto(), a.getModoJuego())));

        // 3. Procesar cada grupo buscando pares
        for (List<Apuesta> grupo : grupos.values()) {
            for (int i = 0; i + 1 < grupo.size(); i += 2) {
                Apuesta a1 = grupo.get(i);
                Apuesta a2 = grupo.get(i + 1);

                // 4. Cambiar estado y guardar
                a1.setEstado(EstadoApuesta.EMPAREJADA);
                a2.setEstado(EstadoApuesta.EMPAREJADA);
                // opcional: registrar hora de emparejamiento si existiera campo

                apuestaRepository.saveAll(Arrays.asList(a1, a2));

                resultados.add(new MatchResultDto(
                        a1.getId(),
                        a2.getId(),
                        a1.getMonto(),
                        a1.getModoJuego()
                ));
            }
        }

        return resultados;
    }

    /**
     * Llave para el agrupamiento por monto y modo de juego.
     */
    private record Key(BigDecimal monto, String modo) {
    }
}
