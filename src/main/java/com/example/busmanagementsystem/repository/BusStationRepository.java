package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.BusStation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;


@Repository
public class BusStationRepository extends InFileRepository<BusStation> {

    protected BusStationRepository(ObjectMapper objectMapper, @Value("${repository.filepath.busstation}") String filePath ) {
        super(filePath, objectMapper,  BusStation.class);
    }

    @Override
    protected String getIdFromEntity(BusStation entity) {
        return entity.getId();
    }
}
