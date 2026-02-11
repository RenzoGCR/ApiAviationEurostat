package org.example.apiaviationeurostat.controllers;

import org.example.apiaviationeurostat.entities.AviationRecord;
import org.example.apiaviationeurostat.services.AviationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aviation")
public class AviationController {
    private final AviationService aviationService;

    public AviationController(AviationService aviationService) {
        this.aviationService = aviationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AviationRecord>> getAll() {
        // ResponseEntity es mejor práctica para controlar códigos HTTP (200, 404, etc.)
        return ResponseEntity.ok(aviationService.getAllRecords());
    }

    @GetMapping("/country/{geo}")
    public ResponseEntity<List<AviationRecord>> getByCountry(@PathVariable String geo) {
        List<AviationRecord> records = aviationService.getByCountry(geo);

        if (records.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no hay datos
        }
        return ResponseEntity.ok(records);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AviationRecord>> search(
            @RequestParam String country,
            @RequestParam(defaultValue = "TOTAL") String type) {

        List<AviationRecord> results = aviationService.geByCountryandType(country, type);
        return ResponseEntity.ok(results);
    }

}
