package com.crduels.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserData {
    private String googleId;
    private String nombre;
    private String email;
    private String imagen;
}
