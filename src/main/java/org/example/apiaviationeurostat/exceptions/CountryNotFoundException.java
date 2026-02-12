package org.example.apiaviationeurostat.exceptions;

/**
 * Excepción personalizada lanzada cuando no se encuentra un país o región geográfica
 * en la base de datos.
 */
public class CountryNotFoundException extends RuntimeException {
    /**
     * Constructor con mensaje de error.
     *
     * @param message el mensaje descriptivo del error.
     */
    public CountryNotFoundException(String message) {
        super(message);
    }
}
