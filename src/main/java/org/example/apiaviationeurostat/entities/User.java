package org.example.apiaviationeurostat.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Entidad que representa a un usuario del sistema.
 * Mapeada a la colección "users" en MongoDB.
 */
@Data
@Document(collection = "users")
public class User {
    /**
     * Identificador único del usuario.
     */
    @Id
    private String id;

    /**
     * Nombre de usuario único para autenticación.
     * Indexado como único en la base de datos para evitar duplicados.
     */
    @Indexed(unique = true)
    private String username;

    /**
     * Contraseña del usuario.
     * Debe almacenarse encriptada, nunca en texto plano.
     */
    private String password;

    /**
     * Lista de roles asignados al usuario (ej. ["ADMIN", "USER"]).
     * Determinan los permisos de acceso en la aplicación.
     */
    private List<String> roles;
}