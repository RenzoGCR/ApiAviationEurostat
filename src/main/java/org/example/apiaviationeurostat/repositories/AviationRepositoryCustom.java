package org.example.apiaviationeurostat.repositories;

import org.example.apiaviationeurostat.dto.AviationFilterDTO; // Asegúrate de importar tu DTO
import org.example.apiaviationeurostat.entities.AviationRecord;
import java.util.List;

//interfaz necesaria para hacer busquedas flexibles, con parametros que
//no esten fijos en la consulta.
public interface AviationRepositoryCustom {
    List<AviationRecord> searchByFilters(AviationFilterDTO filter);
}