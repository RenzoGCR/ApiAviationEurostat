package org.example.apiaviationeurostat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private LocalDateTime timestamp; // Cuándo ocurrió
    private int status;              // Código HTTP (404, 400, 500)
    private String error;            // Tipo de error (ej: "Not Found")
    private String message;          // Mensaje para humanos (ej: "El país XX no existe")
    private String path;             // Endpoint llamado
}
