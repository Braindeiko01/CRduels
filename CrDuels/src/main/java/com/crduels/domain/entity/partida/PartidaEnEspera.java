package com.crduels.domain.entity.partida;

import com.crduels.domain.entity.Jugador;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "partidas_en_espera")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PartidaEnEspera {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    @Convert(converter = ModoJuegoListConverter.class)
    @Column(name = "modos_juego", nullable = false)
    private List<ModoJuego> modosJuego;

    @Column(nullable = false)
    private BigDecimal monto;


}

