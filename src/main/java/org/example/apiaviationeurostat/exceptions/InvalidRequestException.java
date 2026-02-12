package org.example.apiaviationeurostat.exceptions;

/**
 * Excepción personalizada lanzada cuando una solicitud contiene parámetros inválidos
 * o no cumple con las reglas de negocio (ej. rango de fechas incorrecto).
 */
public class InvalidRequestException extends RuntimeException {
    /**
     * Constructor con mensaje de error.
     *
     * @param message el mensaje descriptivo del error.
     */
    public InvalidRequestException(String message) {
        super(message);
    }
}
