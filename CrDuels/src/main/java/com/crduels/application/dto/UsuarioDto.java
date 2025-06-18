package com.crduels.application.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UsuarioDto {
    private UUID id;
    private String nombre;
    private String email;
    private String telefono;
    private String tagClash;
    private String linkAmistad;
    private BigDecimal saldo;
    private Integer reputacion;
}
