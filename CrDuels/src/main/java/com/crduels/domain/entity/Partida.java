package com.crduels.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    private UUID id;

    // Ya existentes
    private String jugador1Id;
    private String jugador2Id;
    private String modoJuego;
    private String estado;
    private LocalDateTime creada;
    private UUID chatId;
    private String resultado;
    private String resultadoJson;

    // Nuevos campos
    private boolean validada;
    private LocalDateTime validadaEn;

    @OneToOne
    @JoinColumn(name = "apuesta_id")
    private Apuesta apuesta;

    @ManyToOne
    @JoinColumn(name = "ganador_id")
    private Usuario ganador;
}
//
//@Entity
//@Table(name = "partida")
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Partida {
//
//    @Id
//    private UUID id;
//
//    @Column(nullable = false)
//    private String jugador1Id;
//
//    @Column(nullable = false)
//    private String jugador2Id;
//
//    @Column(nullable = false)
//    private String modoJuego;
//
//    @Column(nullable = false)
//    private String estado; // ACTIVA, TERMINADA, etc.
//
//    @Column(nullable = false)
//    private LocalDateTime creada;
//
//    private UUID chatId;
//
//    private String resultado;
//}
