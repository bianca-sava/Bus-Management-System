package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusStation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BusStationRepository extends InMemoryRepository<BusStation> {

    @Override
    protected String getIdFromEntity(BusStation entity) {
        return entity.getId();
    }
}
