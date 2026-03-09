# Servicio Web de Autenticación
### GA7-220501096-AA5-EV01

Servicio web REST desarrollado en Java con Spring Boot para el registro e inicio de sesión de usuarios.

---

## Tecnologías utilizadas

- **Java 17+**
- **Spring Boot 3.2** — framework principal
- **Spring Security** — cifrado de contraseñas con BCrypt
- **Spring Data JPA** — acceso a base de datos
- **H2** — base de datos en memoria
- **Maven** — gestor de dependencias

---

## Estructura del proyecto

```
servicio-web/
├── iniciar.bat                          → Script para arrancar el servidor
├── pom.xml                              → Dependencias del proyecto
├── apache-maven-3.9.6/                  → Maven (herramienta de compilación)
└── src/
    └── main/
        ├── java/com/proyecto/servicioauth/
        │   ├── ServicioAuthApplication.java     → Clase principal, punto de entrada
        │   ├── config/
        │   │   └── SecurityConfig.java          → Configuración de seguridad y BCrypt
        │   ├── controller/
        │   │   └── AuthController.java          → Endpoints REST (registro y login)
        │   ├── service/
        │   │   └── AuthService.java             → Lógica de negocio
        │   ├── model/
        │   │   └── Usuario.java                 → Entidad de base de datos
        │   ├── repository/
        │   │   └── UsuarioRepository.java       → Acceso a datos
        │   └── dto/
        │       ├── RegistroRequest.java         → Datos de entrada para registro
        │       ├── LoginRequest.java            → Datos de entrada para login
        │       └── RespuestaDTO.java            → Estructura de respuesta JSON
        └── resources/
            └── application.properties           → Configuración del servidor y BD
```

---

## Servicios disponibles

### 1. Registro de usuario
Crea un nuevo usuario en el sistema.

- **Método:** POST
- **URL:** `http://localhost:8081/api/auth/registro`
- **Body (JSON):**
```json
{
  "username": "juan123",
  "password": "miClave123"
}
```
- **Respuesta exitosa:**
```json
{
  "exito": true,
  "mensaje": "Registro exitoso. Usuario 'juan123' creado correctamente."
}
```
- **Si el usuario ya existe:**
```json
{
  "exito": false,
  "mensaje": "El nombre de usuario 'juan123' ya está en uso. Elija otro."
}
```

---

### 2. Inicio de sesión
Verifica las credenciales de un usuario registrado.

- **Método:** POST
- **URL:** `http://localhost:8081/api/auth/login`
- **Body (JSON):**
```json
{
  "username": "juan123",
  "password": "miClave123"
}
```
- **Autenticación satisfactoria:**
```json
{
  "exito": true,
  "mensaje": "Autenticación satisfactoria. Bienvenido, juan123."
}
```
- **Error en la autenticación:**
```json
{
  "exito": false,
  "mensaje": "Error en la autenticación: credenciales incorrectas."
}
```

---

## Cómo iniciar el servidor

**Requisito:** tener Java 17 o superior instalado.

1. Abrir la carpeta `servicio-web`
2. Hacer doble clic en el archivo **`iniciar.bat`**
3. Esperar a que aparezca en la consola:
```
Tomcat started on port 8081
Started ServicioAuthApplication
```
4. El servicio ya está disponible en `http://localhost:8081`

Para detenerlo, cerrar la ventana de la consola o presionar `Ctrl + C`.

---

## Cómo probar el servicio

Se recomienda usar **Postman** para enviar las peticiones:

1. Abrir Postman
2. Seleccionar método **POST**
3. Ingresar la URL del servicio
4. En la pestaña **Body** seleccionar **raw** y formato **JSON**
5. Escribir el JSON con los datos y hacer clic en **Send**

También se puede acceder a la base de datos desde el navegador en:
`http://localhost:8081/h2-console`
- JDBC URL: `jdbc:h2:mem:authdb`
- Usuario: `sa`
- Contraseña: *(dejar vacío)*

---

## Validaciones implementadas

- El nombre de usuario debe tener entre 3 y 50 caracteres
- La contraseña debe tener mínimo 6 caracteres
- No se permiten campos vacíos
- No se pueden registrar dos usuarios con el mismo nombre
- Las contraseñas se almacenan cifradas con BCrypt, nunca en texto plano
