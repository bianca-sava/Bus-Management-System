package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusStation;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;


@Repository
public class BusStationRepository extends InMemoryRepository<BusStation> {

    public BusStationRepository() {
        this.database = new ConcurrentHashMap<>();
        this.database.put("BS1", new BusStation("BS1", "Central Station", "123 Main St"));
    }

    @Override
    protected String getIdFromEntity(BusStation entity) {
        return entity.getId();
    }
}
