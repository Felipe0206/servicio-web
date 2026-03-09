package com.proyecto.servicioauth.repository;

import com.proyecto.servicioauth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de acceso a datos para la entidad Usuario.
 *
 * Extiende JpaRepository para heredar métodos CRUD básicos como:
 * save(), findById(), findAll(), deleteById(), etc.
 *
 * Spring Data JPA genera automáticamente la implementación en tiempo de ejecución.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * Spring Data JPA genera la consulta SQL automáticamente:
     * SELECT * FROM usuarios WHERE username = ?
     *
     * @param username nombre de usuario a buscar
     * @return Optional con el usuario si existe, o vacío si no existe
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Verifica si ya existe un usuario con el nombre dado.
     * Se usa para validar que el username no esté duplicado en el registro.
     *
     * @param username nombre de usuario a verificar
     * @return true si ya existe, false si está disponible
     */
    boolean existsByUsername(String username);
}
