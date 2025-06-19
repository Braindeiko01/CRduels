package com.crduels.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos para registrar un usuario a través de WhatsApp.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroWhatsAppDto {
    private String nombre;
    private String tagClash;
}
