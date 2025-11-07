package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusTrip;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;


@Repository
public class BusTripRepository extends InFileRepository<BusTrip> {

    protected BusTripRepository(ObjectMapper objectMapper) {
        super("/Bus Management System/src/main/resources/data/busTrip.json", objectMapper, BusTrip.class);
    }

    @Override
    protected String getIdFromEntity(BusTrip entity) {
        return entity.getId();
    }
}
