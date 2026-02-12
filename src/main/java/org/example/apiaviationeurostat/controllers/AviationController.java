package org.example.apiaviationeurostat.controllers;

import org.example.apiaviationeurostat.dto.AviationFilterDTO;
import org.example.apiaviationeurostat.entities.AviationRecord;
import org.example.apiaviationeurostat.services.AviationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los registros de aviación.
 * Proporciona endpoints para consultar y filtrar datos de aviación.
 */
@RestController
@RequestMapping("/api/aviation")
public class AviationController {
    private final AviationService aviationService;

    /**
     * Constructor para inyectar el servicio de aviación.
     *
     * @param aviationService el servicio de lógica de negocio para aviación.
     */
    public AviationController(AviationService aviationService) {
        this.aviationService = aviationService;
    }

    /**
     * Obtiene todos los registros de aviación disponibles.
     *
     * @return una respuesta {@link ResponseEntity} con la lista de todos los registros {@link AviationRecord}.
     */
    @GetMapping("/all")
    public ResponseEntity<List<AviationRecord>> getAll() {
        // ResponseEntity es mejor práctica para controlar códigos HTTP (200, 404, etc.)
        return ResponseEntity.ok(aviationService.getAllRecords());
    }

    /**
     * Obtiene los registros de aviación filtrados por código de país (geo).
     *
     * @param geo el código geográfico del país (ej. "ES").
     * @return una respuesta {@link ResponseEntity} con la lista de registros encontrados,
     *         o un estado 404 Not Found si no existen registros para ese país.
     */
    @GetMapping("/country/{geo}")
    public ResponseEntity<List<AviationRecord>> getByCountry(@PathVariable String geo) {
        List<AviationRecord> records = aviationService.getByCountry(geo);

        if (records.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no hay datos
        }
        return ResponseEntity.ok(records);
    }

    /**
     * Busca registros de aviación por país y tipo de transporte.
     *
     * @param country el código del país a buscar.
     * @param type el tipo de transporte (por defecto "TOTAL").
     * @return una respuesta {@link ResponseEntity} con la lista de registros que coinciden con los criterios.
     */
    @GetMapping("/search")
    public ResponseEntity<List<AviationRecord>> search(
            @RequestParam String country,
            @RequestParam(defaultValue = "TOTAL") String type) {

        List<AviationRecord> results = aviationService.geByCountryandType(country, type);
        return ResponseEntity.ok(results);
    }

    /**
     * Realiza una búsqueda avanzada de registros de aviación utilizando un objeto de filtro.
     *
     * @param filterDTO el objeto {@link AviationFilterDTO} que contiene los criterios de filtrado.
     * @return una respuesta {@link ResponseEntity} con la lista de registros filtrados,
     *         o un estado 204 No Content si no se encuentran coincidencias.
     */
    @PostMapping("/search/advanced")
    public ResponseEntity<List<AviationRecord>> searchAdvanced(@RequestBody AviationFilterDTO filterDTO) {
        List<AviationRecord> results = aviationService.getByAdvancedFilter(filterDTO);

        if(results.isEmpty()){
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay coincidencias
        }
        return ResponseEntity.ok(results);
    }

}
