package org.example.apiaviationeurostat.services;

import org.example.apiaviationeurostat.entities.AviationRecord;
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

}
