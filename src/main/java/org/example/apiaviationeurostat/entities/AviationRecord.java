package org.example.apiaviationeurostat.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Entidad que representa un registro de pasajeros de aviación mensual.
 * Mapeada a la colección "aviation_monthly_passengers" en MongoDB.
 */
@Data
@Document(collection = "aviation_monthly_passengers")
public class AviationRecord {
    /**
     * Identificador único del registro en la base de datos.
     */
    @Id
    private String id;

    /**
     * Código geográfico del país o región (ej. "ES").
     */
    private String geo;

    /**
     * Periodo de tiempo del registro (ej. "2025-01").
     */
    private String time;

    /**
     * Valor numérico del registro, generalmente número de pasajeros.
     * Se usa Long para soportar cifras grandes.
     */
    private Long value;

    /**
     * Cobertura de transporte (ej. "TOTAL", "NAT", "INTL").
     * Mapeado al campo "tra_cov" en la base de datos.
     */
    @Field("tra_cov")
    private String traCov;

    /**
     * Medida de transporte (ej. "PAS_CRD").
     * Mapeado al campo "tra_meas" en la base de datos.
     */
    @Field("tra_meas")
    private String traMeas;

    /**
     * Frecuencia de los datos (opcional).
     */
    private String freq;

    /**
     * Unidad de medida (opcional).
     */
    private String unit;

    /**
     * Horario o programación (opcional).
     */
    private String schedule;

}
