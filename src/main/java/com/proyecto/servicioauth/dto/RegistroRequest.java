package com.proyecto.servicioauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) para la solicitud de registro.
 *
 * Contiene los datos que el cliente envía al endpoint de registro.
 * Las anotaciones de validación garantizan que los datos sean correctos
 * antes de procesarlos en el servicio.
 */
public class RegistroRequest {

    /**
     * Nombre de usuario.
     * - No puede estar vacío o solo con espacios.
     * - Debe tener entre 3 y 50 caracteres.
     */
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 50, message = "El usuario debe tener entre 3 y 50 caracteres")
    private String username;

    /**
     * Contraseña del usuario.
     * - No puede estar vacía.
     * - Debe tener mínimo 6 caracteres por seguridad.
     */
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
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
