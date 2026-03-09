package com.proyecto.servicioauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación.
 *
 * Evidencia: GA7-220501096-AA5-EV01
 * Actividad: Diseño y desarrollo de servicios web
 * Programa: Análisis y Desarrollo de Software - SENA
 *
 * Esta aplicación expone servicios web REST para:
 *  - Registro de usuarios nuevos
 *  - Inicio de sesión (autenticación)
 */
@SpringBootApplication
public class ServicioAuthApplication {

    /**
     * Punto de entrada de la aplicación Spring Boot.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(ServicioAuthApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  Servicio Web de Autenticación - INICIADO   ");
        System.out.println("  URL: http://localhost:8080                  ");
        System.out.println("  H2 Console: http://localhost:8080/h2-console");
        System.out.println("==============================================");
    }
}
