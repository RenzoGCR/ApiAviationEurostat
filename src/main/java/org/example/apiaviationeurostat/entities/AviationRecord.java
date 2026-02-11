package org.example.apiaviationeurostat.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "aviation_monthly_passengers")
public class AviationRecord {
    @Id
    private String id;

    // Campos principales
    private String geo;      // Ejemplo: "ES"
    private String time;     // Ejemplo: "2025-01"
    private Long value;      // Ejemplo: 39305219 (Long por si los números son muy grandes)

    // Mapeo de nombres con guiones bajos
    @Field("tra_cov")
    private String traCov;   // Ejemplo: "TOTAL", "NAT", "INTL"
    @Field("tra_meas")
    private String traMeas;  // Ejemplo: "PAS_CRD"

    // Campos de contexto (opcionales si siempre son iguales, pero buenos para tener)
    private String freq;
    private String unit;
    private String schedule;

}
