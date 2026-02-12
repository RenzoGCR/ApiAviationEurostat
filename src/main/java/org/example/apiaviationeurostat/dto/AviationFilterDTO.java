package org.example.apiaviationeurostat.dto;

import lombok.Data;
import java.util.List;

@Data
public class AviationFilterDTO {
    private List<String> countries; // Permite buscar varios países a la vez: ["ES", "FR", "DE"]
    private String timeStart;       // "2025-01"
    private String timeEnd;         // "2025-06"
    private Long minPassengers;     // Filtrar vuelos con más de X pasajeros
}