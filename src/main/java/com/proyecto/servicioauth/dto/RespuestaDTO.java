package com.proyecto.servicioauth.dto;

/**
 * DTO genérico para las respuestas de la API.
 *
 * Se utiliza para retornar al cliente un mensaje descriptivo
 * y un indicador de si la operación fue exitosa o no.
 *
 * Ejemplo de respuesta JSON exitosa:
 * {
 *   "exito": true,
 *   "mensaje": "Autenticación satisfactoria"
 * }
 *
 * Ejemplo de respuesta JSON con error:
 * {
 *   "exito": false,
 *   "mensaje": "Error en la autenticación: credenciales incorrectas"
 * }
 */
public class RespuestaDTO {

    /** Indica si la operación fue exitosa (true) o fallida (false) */
    private boolean exito;

    /** Mensaje descriptivo del resultado de la operación */
    private String mensaje;

    /**
     * Constructor con parámetros.
     *
     * @param exito   true si fue exitoso, false si hubo error
     * @param mensaje descripción del resultado
     */
    public RespuestaDTO(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }

    // ---- Getters ----

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }
}
