package com.proyecto.servicioauth.service;

import com.proyecto.servicioauth.dto.LoginRequest;
import com.proyecto.servicioauth.dto.RegistroRequest;
import com.proyecto.servicioauth.dto.RespuestaDTO;
import com.proyecto.servicioauth.model.Usuario;
import com.proyecto.servicioauth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación.
 *
 * Contiene la lógica de negocio para:
 *  1. Registro de nuevos usuarios.
 *  2. Inicio de sesión (autenticación de credenciales).
 *
 * Principio de capas: el controlador solo recibe/envía datos,
 * este servicio es quien procesa y aplica las reglas de negocio.
 */
@Service
public class AuthService {

    /**
     * Repositorio para acceder y persistir datos de usuarios en la base de datos.
     * Spring lo inyecta automáticamente.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Codificador de contraseñas BCrypt.
     * Convierte la contraseña en texto plano a un hash seguro
     * y también verifica contraseñas contra su hash almacenado.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================================================================
    // SERVICIO 1: REGISTRO DE USUARIO
    // ================================================================

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * Proceso:
     * 1. Verifica que el nombre de usuario no esté ya en uso.
     * 2. Cifra la contraseña con BCrypt antes de guardarla.
     * 3. Guarda el nuevo usuario en la base de datos.
     *
     * @param request DTO con el username y password del nuevo usuario
     * @return RespuestaDTO con el resultado del registro
     */
    public RespuestaDTO registrar(RegistroRequest request) {

        // Verificar si el nombre de usuario ya está registrado
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            // El usuario ya existe, no se puede duplicar
            return new RespuestaDTO(false,
                "El nombre de usuario '" + request.getUsername() + "' ya está en uso. Elija otro.");
        }

        // Cifrar la contraseña antes de guardarla (NUNCA guardar texto plano)
        String passwordCifrada = passwordEncoder.encode(request.getPassword());

        // Crear el nuevo objeto usuario con los datos validados
        Usuario nuevoUsuario = new Usuario(request.getUsername(), passwordCifrada);

        // Persistir el usuario en la base de datos
        usuarioRepository.save(nuevoUsuario);

        // Retornar respuesta de éxito
        return new RespuestaDTO(true,
            "Registro exitoso. Usuario '" + request.getUsername() + "' creado correctamente.");
    }

    // ================================================================
    // SERVICIO 2: INICIO DE SESIÓN (AUTENTICACIÓN)
    // ================================================================

    /**
     * Autentica un usuario verificando sus credenciales.
     *
     * Proceso:
     * 1. Busca al usuario por nombre de usuario en la base de datos.
     * 2. Si no existe, retorna error de autenticación.
     * 3. Si existe, compara la contraseña ingresada contra el hash almacenado.
     * 4. Retorna éxito o error según el resultado de la comparación.
     *
     * Seguridad: BCrypt compara el texto plano con el hash sin descifrarlo,
     * garantizando que la contraseña real nunca sea expuesta.
     *
     * @param request DTO con el username y password del intento de login
     * @return RespuestaDTO indicando si la autenticación fue satisfactoria o no
     */
    public RespuestaDTO iniciarSesion(LoginRequest request) {

        // Buscar al usuario en la base de datos por su nombre de usuario
        var usuarioOpcional = usuarioRepository.findByUsername(request.getUsername());

        // Si el usuario no existe en el sistema
        if (usuarioOpcional.isEmpty()) {
            // No revelar si el usuario existe o no (buena práctica de seguridad)
            return new RespuestaDTO(false, "Error en la autenticación: credenciales incorrectas.");
        }

        // Obtener el usuario encontrado
        Usuario usuario = usuarioOpcional.get();

        // Verificar si la contraseña ingresada coincide con el hash almacenado
        boolean passwordCorrecta = passwordEncoder.matches(request.getPassword(), usuario.getPassword());

        if (passwordCorrecta) {
            // Credenciales correctas: autenticación satisfactoria
            return new RespuestaDTO(true,
                "Autenticación satisfactoria. Bienvenido, " + usuario.getUsername() + ".");
        } else {
            // Contraseña incorrecta: error en la autenticación
            return new RespuestaDTO(false, "Error en la autenticación: credenciales incorrectas.");
        }
    }
}
