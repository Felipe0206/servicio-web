package com.proyecto.servicioauth.dto;

// DTO de respuesta: indica si la operacion fue exitosa y un mensaje descriptivo
public class RespuestaDTO {

    private boolean exito;
    private String mensaje;

    public RespuestaDTO(boolean exito, String mensaje) {
        this.exito = exito;
        this.mensaje = mensaje;
    }

    public boolean isExito() { return exito; }
    public String getMensaje() { return mensaje; }
}
