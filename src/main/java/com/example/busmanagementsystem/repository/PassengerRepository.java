package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerRepository extends InMemoryRepository<Passenger> {

    @Override
    protected String getIdFromEntity(Passenger entity) {
        return entity.getId();
    }
}
