package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusStation;
import org.springframework.stereotype.Repository;


@Repository
public class BusStationRepository extends InMemoryRepository<BusStation> {

    @Override
    protected String getIdFromEntity(BusStation entity) {
        return entity.getId();
    }
}
