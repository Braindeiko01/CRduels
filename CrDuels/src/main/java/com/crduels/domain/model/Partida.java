package com.crduels.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "partidas")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "apuesta_id", columnDefinition = "uuid", nullable = false)
    private UUID apuestaId;

    @Column(name = "ganador_id", columnDefinition = "uuid")
    private UUID ganadorId;

    @Column(nullable = false)
    private boolean validada;

    @Column(name = "resultado_json")
    private String resultadoJson;

    @Column(name = "validada_en")
    private LocalDateTime validadaEn;
}
