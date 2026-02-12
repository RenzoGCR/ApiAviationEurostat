package org.example.apiaviationeurostat.exceptions;

import org.example.apiaviationeurostat.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Manejador global de excepciones para la aplicación.
 * Intercepta las excepciones lanzadas por los controladores y devuelve respuestas de error estandarizadas.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de tipo {@link InvalidRequestException}.
     * Devuelve un error 400 Bad Request cuando la solicitud contiene datos inválidos.
     *
     * @param ex la excepción capturada.
     * @param request la solicitud web actual.
     * @return una respuesta {@link ResponseEntity} con los detalles del error.
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidRequest(InvalidRequestException ex, WebRequest request) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "") // Obtiene la URL llamada
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de tipo {@link CountryNotFoundException}.
     * Devuelve un error 404 Not Found cuando no se encuentran datos para un país específico.
     *
     * @param ex la excepción capturada.
     * @param request la solicitud web actual.
     * @return una respuesta {@link ResponseEntity} con los detalles del error.
     */
    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCountryNotFound(CountryNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja cualquier otra excepción no controlada específicamente.
     * Devuelve un error 500 Internal Server Error.
     *
     * @param ex la excepción capturada.
     * @param request la solicitud web actual.
     * @return una respuesta {@link ResponseEntity} con los detalles del error genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ha ocurrido un error inesperado",
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}