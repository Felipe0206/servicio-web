package com.proyecto.servicioauth.controller;

import com.proyecto.servicioauth.dto.LoginRequest;
import com.proyecto.servicioauth.dto.RegistroRequest;
import com.proyecto.servicioauth.dto.RespuestaDTO;
import com.proyecto.servicioauth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para los servicios de autenticación.
 *
 * Expone los endpoints HTTP para:
 *  - POST /api/auth/registro  → Registro de nuevos usuarios
 *  - POST /api/auth/login     → Inicio de sesión / autenticación
 *
 * Todos los endpoints reciben y responden en formato JSON.
 *
 * Evidencia: GA7-220501096-AA5-EV01
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * Servicio de autenticación inyectado automáticamente por Spring.
     * Contiene la lógica de negocio para registro e inicio de sesión.
     */
    @Autowired
    private AuthService authService;

    // ================================================================
    // ENDPOINT 1: REGISTRO DE USUARIO
    // POST /api/auth/registro
    // ================================================================

    /**
     * Servicio web para registrar un nuevo usuario.
     *
     * Recibe un JSON con 'username' y 'password', valida los datos
     * y crea el usuario en la base de datos si no existe previamente.
     *
     * Ejemplo de solicitud (Body JSON):
     * {
     *   "username": "juan123",
     *   "password": "miClave123"
     * }
     *
     * Respuesta exitosa (HTTP 201 Created):
     * {
     *   "exito": true,
     *   "mensaje": "Registro exitoso. Usuario 'juan123' creado correctamente."
     * }
     *
     * @param request datos de registro validados automáticamente
     * @return ResponseEntity con el resultado del registro y código HTTP
     */
    @PostMapping("/registro")
    public ResponseEntity<RespuestaDTO> registro(@Valid @RequestBody RegistroRequest request) {

        // Delegar la lógica al servicio de autenticación
        RespuestaDTO respuesta = authService.registrar(request);

        if (respuesta.isExito()) {
            // HTTP 201 Created: usuario registrado correctamente
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } else {
            // HTTP 409 Conflict: el usuario ya existe
            return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
        }
    }

    // ================================================================
    // ENDPOINT 2: INICIO DE SESIÓN (LOGIN)
    // POST /api/auth/login
    // ================================================================

    /**
     * Servicio web para el inicio de sesión de un usuario.
     *
     * Recibe un JSON con 'username' y 'password', verifica las credenciales
     * contra los datos almacenados y retorna si la autenticación fue exitosa.
     *
     * Ejemplo de solicitud (Body JSON):
     * {
     *   "username": "juan123",
     *   "password": "miClave123"
     * }
     *
     * Respuesta exitosa (HTTP 200 OK):
     * {
     *   "exito": true,
     *   "mensaje": "Autenticación satisfactoria. Bienvenido, juan123."
     * }
     *
     * Respuesta con error (HTTP 401 Unauthorized):
     * {
     *   "exito": false,
     *   "mensaje": "Error en la autenticación: credenciales incorrectas."
     * }
     *
     * @param request credenciales de inicio de sesión validadas automáticamente
     * @return ResponseEntity con el resultado de la autenticación y código HTTP
     */
    @PostMapping("/login")
    public ResponseEntity<RespuestaDTO> login(@Valid @RequestBody LoginRequest request) {

        // Delegar la verificación de credenciales al servicio
        RespuestaDTO respuesta = authService.iniciarSesion(request);

        if (respuesta.isExito()) {
            // HTTP 200 OK: autenticación satisfactoria
            return ResponseEntity.ok(respuesta);
        } else {
            // HTTP 401 Unauthorized: error en la autenticación
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }
    }
}
