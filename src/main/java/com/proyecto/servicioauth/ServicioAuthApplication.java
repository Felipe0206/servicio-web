package com.proyecto.servicioauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// GA7-220501096-AA5-EV01 - Servicio Web de Autenticacion - Cafe Bar
@SpringBootApplication
public class ServicioAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioAuthApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  Servicio Web de Autenticacion - INICIADO   ");
        System.out.println("  URL: http://localhost:8081                  ");
        System.out.println("  H2 Console: http://localhost:8081/h2-console");
        System.out.println("==============================================");
    }
}
