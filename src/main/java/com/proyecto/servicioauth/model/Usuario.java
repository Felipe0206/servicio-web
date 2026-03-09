package com.proyecto.servicioauth.model;

import jakarta.persistence.*;

/**
 * Entidad que representa un usuario en la base de datos.
 *
 * Cada usuario tiene un ID único, un nombre de usuario (único)
 * y una contraseña almacenada de forma cifrada (hash BCrypt).
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Identificador único del usuario (generado automáticamente).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario. Debe ser único y no puede estar vacío.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Contraseña cifrada con BCrypt.
     * NUNCA se almacena la contraseña en texto plano.
     */
    @Column(nullable = false)
    private String password;

    // ---- Constructores ----

    /** Constructor vacío requerido por JPA */
    public Usuario() {}

    /**
     * Constructor con todos los campos.
     *
     * @param username nombre de usuario
     * @param password contraseña cifrada
     */
    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // ---- Getters y Setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", username='" + username + "'}";
    }
}
