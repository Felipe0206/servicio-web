package com.proyecto.servicioauth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.servicioauth.dto.LoginRequest;
import com.proyecto.servicioauth.dto.RegistroRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración para los servicios web de autenticación.
 *
 * Verifica el comportamiento de los endpoints /registro y /login
 * con diferentes escenarios: datos válidos, duplicados e inválidos.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Prueba: Registro exitoso de un nuevo usuario.
     * Se espera respuesta HTTP 201 y exito=true.
     */
    @Test
    public void testRegistroExitoso() throws Exception {
        RegistroRequest request = new RegistroRequest();
        request.setUsername("usuarioPrueba");
        request.setPassword("clave123");

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.exito").value(true))
            .andExpect(jsonPath("$.mensaje").value(
                org.hamcrest.Matchers.containsString("Registro exitoso")));
    }

    /**
     * Prueba: Login con credenciales correctas.
     * Se espera respuesta HTTP 200 y mensaje de autenticación satisfactoria.
     */
    @Test
    public void testLoginExitoso() throws Exception {
        // Primero registrar el usuario
        RegistroRequest registro = new RegistroRequest();
        registro.setUsername("loginUser");
        registro.setPassword("segura456");

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registro)))
            .andExpect(status().isCreated());

        // Luego iniciar sesión con las mismas credenciales
        LoginRequest login = new LoginRequest();
        login.setUsername("loginUser");
        login.setPassword("segura456");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.exito").value(true))
            .andExpect(jsonPath("$.mensaje").value(
                org.hamcrest.Matchers.containsString("Autenticación satisfactoria")));
    }

    /**
     * Prueba: Login con contraseña incorrecta.
     * Se espera respuesta HTTP 401 y error en la autenticación.
     */
    @Test
    public void testLoginCredencialesIncorrectas() throws Exception {
        // Registrar usuario
        RegistroRequest registro = new RegistroRequest();
        registro.setUsername("otroUser");
        registro.setPassword("claveReal");

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registro)))
            .andExpect(status().isCreated());

        // Intentar login con contraseña incorrecta
        LoginRequest login = new LoginRequest();
        login.setUsername("otroUser");
        login.setPassword("claveErronea");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.exito").value(false))
            .andExpect(jsonPath("$.mensaje").value(
                org.hamcrest.Matchers.containsString("Error en la autenticación")));
    }
}
