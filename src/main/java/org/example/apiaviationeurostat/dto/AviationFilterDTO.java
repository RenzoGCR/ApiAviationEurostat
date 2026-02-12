package org.example.apiaviationeurostat.dto;

import lombok.Data;
import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) para filtrar registros de aviación.
 * Se utiliza en búsquedas avanzadas para encapsular múltiples criterios de filtrado.
 */
@Data
public class AviationFilterDTO {
    /**
     * Lista de códigos de países para filtrar (ej. ["ES", "FR", "DE"]).
     */
    private List<String> countries;

    /**
     * Fecha de inicio del periodo de búsqueda (formato "YYYY-MM").
     */
    private String timeStart;

    /**
     * Fecha de fin del periodo de búsqueda (formato "YYYY-MM").
     */
    private String timeEnd;

    /**
     * Número mínimo de pasajeros para filtrar los registros.
     */
    private Long minPassengers;
}