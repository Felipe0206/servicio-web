package com.proyecto.servicioauth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO (Data Transfer Object) para la solicitud de inicio de sesión.
 *
 * Contiene las credenciales que el cliente envía al endpoint de login.
 * Ambos campos son obligatorios para poder realizar la autenticación.
 */
public class LoginRequest {

    /**
     * Nombre de usuario con el que se intenta iniciar sesión.
     * No puede estar vacío.
     */
    @NotBlank(message = "El nombre de usuario es requerido")
    private String username;

    /**
     * Contraseña del usuario.
     * No puede estar vacía.
     */
    @NotBlank(message = "La contraseña es requerida")
    private String password;

    // ---- Getters y Setters ----

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
