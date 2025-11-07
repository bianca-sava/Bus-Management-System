package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusStation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;


@Repository
public class BusStationRepository extends InFileRepository<BusStation> {

    public BusStationRepository(ObjectMapper objectMapper) {
        super("/Bus Management System/src/main/resources/data/busStation.json", objectMapper, BusStation.class);

    }

    @Override
    protected String getIdFromEntity(BusStation entity) {
        return entity.getId();
    }
}
