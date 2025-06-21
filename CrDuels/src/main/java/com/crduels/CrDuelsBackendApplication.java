package com.crduels;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CrDuelsBackendApplication {

    public static void main(String[] args) {
        log.info("Iniciando aplicacion CRDuels");
        SpringApplication.run(CrDuelsBackendApplication.class, args);
    }
}
