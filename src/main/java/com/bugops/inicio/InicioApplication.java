package com.bugops.inicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Clase principal de la aplicación Inicio.
 * Esta clase inicia la aplicación Spring Boot.
 */
@SpringBootApplication
@EntityScan("com.bugops.inicio.model")
public class InicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(InicioApplication.class, args);
	}

}
