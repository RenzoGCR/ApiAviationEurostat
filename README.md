# ✈ API Aviation Eurostat

API REST desarrollada con **Spring Boot** para consultar y analizar estadísticas de transporte aéreo en Europa (datos de Eurostat). El proyecto permite realizar consultas públicas de datos y búsquedas avanzadas protegidas mediante autenticación y roles.

## 📚 Tecnologías

* **Java 17** (o superior)
* **Spring Boot 3.4.2**
* **Spring Data MongoDB** (Persistencia)
* **Spring Security** (Autenticación y Autorización)
* **MongoDB Atlas** (Base de datos en la nube)
* **Maven** (Gestor de dependencias)

## ⚙ Configuración e Instalación

### 1. Requisitos previos
* Tener Java JDK instalado.
* Tener una cuenta en MongoDB Atlas (o una instancia local de MongoDB).

### 2. Variables de Entorno
El proyecto utiliza variables en `application.properties` para no exponer credenciales. Debes configurar la siguiente variable de entorno en tu IDE o sistema operativo:

* `MONGODB_URI`: Tu cadena de conexión a MongoDB Atlas.
    * *Ejemplo:* `mongodb+srv://usuario:password@cluster.mongodb.net/eurostat_data`

### 3. Base de Datos (MongoDB)
El proyecto espera dos colecciones en la base de datos `eurostat_data`:

1.  **`aviation_monthly_passengers`**: Contiene los registros de vuelos.
2.  **`users`**: Contiene los usuarios para autenticación.

#### Estructura de Usuario (Colección `users`)
Las contraseñas se almacenan encriptadas con **BCrypt** con 10 rondas. Ejemplo de documento JSON para insertar manualmente en Atlas:

```json
{
  "_class": "org.example.apiaviationeurostat.entities.User",
  "username": "admin",
  "password": "$2a$10$...", // Hash BCrypt de tu contraseña
  "roles": ["ADMIN", "USER"]
}