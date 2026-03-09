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

// Controlador REST: expone los endpoints de registro y login
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // POST /api/auth/registro - Registra un nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<RespuestaDTO> registro(@Valid @RequestBody RegistroRequest request) {
        RespuestaDTO respuesta = authService.registrar(request);
        if (respuesta.isExito()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
        }
    }

    // POST /api/auth/login - Autentica un usuario existente
    @PostMapping("/login")
    public ResponseEntity<RespuestaDTO> login(@Valid @RequestBody LoginRequest request) {
        RespuestaDTO respuesta = authService.iniciarSesion(request);
        if (respuesta.isExito()) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
        }
    }
}
