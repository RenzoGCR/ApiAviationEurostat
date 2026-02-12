package org.example.apiaviationeurostat.services;

import org.example.apiaviationeurostat.dto.AviationFilterDTO;
import org.example.apiaviationeurostat.entities.AviationRecord;
import org.example.apiaviationeurostat.exceptions.InvalidRequestException;
import org.example.apiaviationeurostat.repositories.AviationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de lógica de negocio para la gestión de registros de aviación.
 * Actúa como intermediario entre el controlador y el repositorio.
 */
@Service
public class AviationService {
    private final AviationRepository repository;

    /**
     * Constructor para inyectar el repositorio de aviación.
     *
     * @param repository el repositorio de datos.
     */
    public AviationService(AviationRepository repository) {
        this.repository = repository;
    }

    /**
     * Obtiene todos los registros de aviación disponibles en la base de datos.
     *
     * @return una lista completa de {@link AviationRecord}.
     */
    public List<AviationRecord> getAllRecords(){
        return repository.findAll();
    }

    /**
     * Busca registros de aviación por código de país.
     * Realiza un saneamiento básico del código de entrada.
     *
     * @param countryCode el código del país (ej. "ES").
     * @return una lista de registros para el país especificado, o null si el código es nulo.
     */
    public List<AviationRecord> getByCountry(String countryCode){
        if (countryCode==null){
            //Logica si el usuario no introduce un codigo de pais
            return null;
        } else {
            return repository.findByGeo(countryCode.toUpperCase());
        }
    }

    /**
     * Busca registros por país y tipo de transporte.
     * Convierte los parámetros a mayúsculas para asegurar la coincidencia.
     *
     * @param countryCode el código del país.
     * @param type el tipo de transporte.
     * @return una lista de registros que coinciden con ambos criterios, o null si algún parámetro falta.
     */
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

    /**
     * Busca registros correspondientes a un mes específico.
     *
     * @param month el mes en formato "YYYY-MM".
     * @return una lista de registros para ese mes.
     */
    public List<AviationRecord> getByMonth(String month){
        return repository.findByTime(month);
    }

    /**
     * Busca registros con un número de pasajeros superior a un valor dado.
     *
     * @param value el valor mínimo de pasajeros.
     * @return una lista de los registros con mayor tráfico.
     */
    public List<AviationRecord> getTopPassengers(Long value){
        return repository.findByValueGreaterThan(value);
    }

    /**
     * Realiza una búsqueda avanzada con validación de reglas de negocio.
     * Valida que la fecha de inicio no sea posterior a la fecha de fin antes de consultar.
     *
     * @param filter el objeto {@link AviationFilterDTO} con los filtros.
     * @return una lista de registros filtrados.
     * @throws InvalidRequestException si el rango de fechas es inválido.
     */
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
