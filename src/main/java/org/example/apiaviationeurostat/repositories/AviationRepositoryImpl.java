package org.example.apiaviationeurostat.repositories;

import org.example.apiaviationeurostat.dto.AviationFilterDTO;
import org.example.apiaviationeurostat.entities.AviationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AviationRepositoryImpl implements AviationRepositoryCustom{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<AviationRecord> searchByFilters(AviationFilterDTO filter) {
        Query query = new Query();

        // 1. Filtrar por lista de países (geo) si viene en el DTO
        if (filter.getCountries() != null && !filter.getCountries().isEmpty()) {
            query.addCriteria(Criteria.where("geo").in(filter.getCountries()));
        }

        // 2. Filtrar por rango de fechas (time)
        // MongoDB guarda strings "YYYY-MM", así que la comparación lexicográfica funciona
        if (filter.getTimeStart() != null && filter.getTimeEnd() != null) {
            query.addCriteria(Criteria.where("time").gte(filter.getTimeStart()).lte(filter.getTimeEnd()));
        } else if (filter.getTimeStart() != null) {
            query.addCriteria(Criteria.where("time").gte(filter.getTimeStart()));
        } else if (filter.getTimeEnd() != null) {
            query.addCriteria(Criteria.where("time").lte(filter.getTimeEnd()));
        }

        // 3. Filtrar por valor mínimo de pasajeros
        if (filter.getMinPassengers() != null) {
            query.addCriteria(Criteria.where("value").gte(filter.getMinPassengers()));
        }

        // Ejecutamos la consulta
        return mongoTemplate.find(query, AviationRecord.class);
    }
}
