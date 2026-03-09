package com.proyecto.servicioauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de Spring Security.
 *
 * Define:
 * 1. Qué rutas son públicas (accesibles sin autenticación).
 * 2. El algoritmo de cifrado de contraseñas (BCrypt).
 *
 * BCrypt es el estándar recomendado para almacenar contraseñas:
 * genera un hash diferente cada vez (salt aleatorio) y es
 * computacionalmente costoso para resistir ataques de fuerza bruta.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define la cadena de filtros de seguridad HTTP.
     *
     * Permite acceso público a los endpoints de autenticación
     * y a la consola H2 para fines de demostración.
     *
     * @param http objeto de configuración de seguridad HTTP
     * @return SecurityFilterChain configurado
     * @throws Exception si hay error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF para APIs REST (se usa en formularios web tradicionales)
            .csrf(AbstractHttpConfigurer::disable)

            // Deshabilitar X-Frame-Options para permitir la consola H2 en iframe
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            // Definir qué URLs son públicas y cuáles requieren autenticación
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos: registro y login no requieren autenticación previa
                .requestMatchers("/api/auth/**").permitAll()
                // Consola H2 accesible para desarrollo
                .requestMatchers("/h2-console/**").permitAll()
                // Cualquier otra ruta requiere estar autenticado
                .anyRequest().authenticated()
            );

        return http.build();
    }

    /**
     * Bean del codificador de contraseñas BCrypt.
     *
     * BCrypt aplica un factor de trabajo (por defecto 10 rondas)
     * que hace que calcular el hash sea lento, dificultando
     * ataques de diccionario y fuerza bruta.
     *
     * @return instancia de BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt con factor de trabajo 10 (balance entre seguridad y rendimiento)
        return new BCryptPasswordEncoder(10);
    }
}
