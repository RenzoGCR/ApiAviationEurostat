package org.example.apiaviationeurostat.repositories;

import org.example.apiaviationeurostat.entities.AviationRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AviationRepository extends MongoRepository<AviationRecord, String> {
    // Buscar por país (geo)
    List<AviationRecord> findByGeo(String geo);

    // Buscar por país y tipo de transporte (ej: ES y TOTAL)
    List<AviationRecord> findByGeoAndTraCov(String geo, String traCov);

    // Buscar registros de una fecha específica
    List<AviationRecord> findByTime(String time);

    // Buscar top de pasajeros (mayor a X)
    List<AviationRecord> findByValueGreaterThan(Long value);
}
