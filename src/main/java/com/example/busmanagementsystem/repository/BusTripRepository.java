package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusTrip;
import org.springframework.stereotype.Repository;


@Repository
public class BusTripRepository extends InMemoryRepository<BusTrip> {

    @Override
    protected String getIdFromEntity(BusTrip entity) {
        return entity.getId();
    }
}
