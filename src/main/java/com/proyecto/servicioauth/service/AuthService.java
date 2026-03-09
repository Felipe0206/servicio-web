package com.proyecto.servicioauth.service;

import com.proyecto.servicioauth.dto.LoginRequest;
import com.proyecto.servicioauth.dto.RegistroRequest;
import com.proyecto.servicioauth.dto.RespuestaDTO;
import com.proyecto.servicioauth.model.Usuario;
import com.proyecto.servicioauth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// Servicio de autenticacion: logica de negocio para registro e inicio de sesion
@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registra un nuevo usuario en el sistema
    public RespuestaDTO registrar(RegistroRequest request) {

        // Verificar si el usuario ya existe
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            return new RespuestaDTO(false,
                "El nombre de usuario '" + request.getUsername() + "' ya está en uso. Elija otro.");
        }

        // Cifrar la contraseña con BCrypt antes de guardar
        String passwordCifrada = passwordEncoder.encode(request.getPassword());

        // Guardar el nuevo usuario en la base de datos
        usuarioRepository.save(new Usuario(request.getUsername(), passwordCifrada));

        return new RespuestaDTO(true,
            "Registro exitoso. Usuario '" + request.getUsername() + "' creado correctamente.");
    }

    // Autentica un usuario verificando sus credenciales
    public RespuestaDTO iniciarSesion(LoginRequest request) {

        // Buscar el usuario en la base de datos
        var usuarioOpcional = usuarioRepository.findByUsername(request.getUsername());

        if (usuarioOpcional.isEmpty()) {
            return new RespuestaDTO(false, "Error en la autenticación: credenciales incorrectas.");
        }

        Usuario usuario = usuarioOpcional.get();

        // Verificar si la contraseña coincide con el hash almacenado
        boolean passwordCorrecta = passwordEncoder.matches(request.getPassword(), usuario.getPassword());

        if (passwordCorrecta) {
            return new RespuestaDTO(true,
                "Autenticación satisfactoria. Bienvenido, " + usuario.getUsername() + ".");
        } else {
            return new RespuestaDTO(false, "Error en la autenticación: credenciales incorrectas.");
        }
    }
}
