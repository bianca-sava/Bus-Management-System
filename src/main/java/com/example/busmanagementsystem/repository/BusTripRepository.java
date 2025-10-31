package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusTrip;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BusTripRepository extends InMemoryRepository<BusTrip> {


    @Override
    protected String getIdFromEntity(BusTrip entity) {
        return entity.getId();
    }
}
