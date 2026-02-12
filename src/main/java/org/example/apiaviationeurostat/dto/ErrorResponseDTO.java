package org.example.apiaviationeurostat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Objeto de Transferencia de Datos (DTO) para representar respuestas de error estandarizadas.
 * Se utiliza para devolver información detallada sobre errores ocurridos en la API.
 */
@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    /**
     * Marca de tiempo de cuándo ocurrió el error.
     */
    private LocalDateTime timestamp;

    /**
     * Código de estado HTTP asociado al error (ej. 404, 400, 500).
     */
    private int status;

    /**
     * Descripción breve del tipo de error (ej. "Not Found").
     */
    private String error;

    /**
     * Mensaje detallado y legible para el usuario sobre la causa del error.
     */
    private String message;

    /**
     * La ruta del endpoint donde se originó el error.
     */
    private String path;
}
