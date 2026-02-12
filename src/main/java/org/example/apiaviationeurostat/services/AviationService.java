package org.example.apiaviationeurostat.services;

import org.example.apiaviationeurostat.dto.AviationFilterDTO;
import org.example.apiaviationeurostat.entities.AviationRecord;
import org.example.apiaviationeurostat.exceptions.InvalidRequestException;
import org.example.apiaviationeurostat.repositories.AviationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AviationService {
    private final AviationRepository repository;

    public AviationService(AviationRepository repository) {
        this.repository = repository;
    }

    // 1. Obtener todos los registros
    public List<AviationRecord> getAllRecords(){
        return repository.findAll();
    }

    // 2. Buscar por país (Añadimos lógica de negocio: saneamiento de input)
    public List<AviationRecord> getByCountry(String countryCode){
        if (countryCode==null){
            //Logica si el usuario no introduce un codigo de pais
            return null;
        } else {
            return repository.findByGeo(countryCode.toUpperCase());
        }
    }

    // 3. Búsqueda combinada (País + Tipo de Transporte)
    public List<AviationRecord> geByCountryandType(String countryCode, String type){
        String countryCodeUpper = countryCode.toUpperCase();
        String typeUpper = type.toUpperCase();
        if (countryCode==null || type==null){
            //Logica si el usuario no introduce un codigo de pais o tipo de transporte
            return null;
        }else {
            return repository.findByGeoAndTraCov(countryCodeUpper,typeUpper);
        }
    }

    // 4. Obtener registros a partir de un mes dado
    public List<AviationRecord> getByMonth(String month){
        return repository.findByTime(month);
    }

    // 5. Buscar top pasajeros (mayor a X)
    public List<AviationRecord> getTopPassengers(Long value){
        return repository.findByValueGreaterThan(value);
    }

    public List<AviationRecord> getByAdvancedFilter(AviationFilterDTO filter) {
        // 2. VALIDACIÓN: Fecha Inicio < Fecha Fin
        // Como el formato es "YYYY-MM", la comparación de Strings (lexicográfica) funciona perfectamente.
        if (filter.getTimeStart() != null && filter.getTimeEnd() != null) {
            // compareTo devuelve > 0 si timeStart es "mayor" (posterior) a timeEnd
            if (filter.getTimeStart().compareTo(filter.getTimeEnd()) > 0) {
                throw new InvalidRequestException(
                        "Rango de fechas inválido: La fecha de inicio (" + filter.getTimeStart() +
                                ") no puede ser posterior a la fecha de fin (" + filter.getTimeEnd() + ")"
                );
            }
        }

        // 3. Si pasa la validación, llamamos al repositorio personalizado
        return repository.searchByFilters(filter);
    }

}
