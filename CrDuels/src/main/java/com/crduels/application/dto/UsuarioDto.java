package com.crduels.application.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDto {

    @NotBlank
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @Email(message = "Correo inválido")
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Número de teléfono inválido")
    private String telefono;

    @Pattern(
            regexp = "^(https://link\\.clashroyale\\.com/invite/friend\\?tag=[A-Z0-9]+.*)?$",
            message = "Enlace de amistad inválido"
    )
    private String linkAmistad;

}
