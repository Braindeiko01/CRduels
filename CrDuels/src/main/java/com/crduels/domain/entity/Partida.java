package com.crduels.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa el resultado de una apuesta.
 */

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apuesta_id", nullable = false)
    private Apuesta apuesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ganador_id")
    private Usuario ganador;

    @Column(nullable = false)
    private boolean validada;

    @Column(name = "resultado_json")
    private String resultadoJson;

    @Column(name = "validada_en")
    private LocalDateTime validadaEn;
}
