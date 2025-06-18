package com.crduels.application.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UsuarioDto {
    private UUID id;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @Email(message = "Correo inválido")
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Número de teléfono inválido")
    private String telefono;

    @NotBlank
    @Pattern(regexp = "^#?[A-Z0-9]{5,12}$", message = "Tag inválido")
    private String tagClash;

    @Pattern(
        regexp = "^(https://link\\.clashroyale\\.com/invite/friend\\?tag=[A-Z0-9]+)?$",
        message = "Enlace de amistad inválido"
    )
    private String linkAmistad;

    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
    private BigDecimal saldo;
    private Integer reputacion;
}
