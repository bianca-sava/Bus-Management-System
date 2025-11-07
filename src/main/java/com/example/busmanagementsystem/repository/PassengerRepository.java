package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.Passenger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerRepository extends InFileRepository<Passenger> {

    protected PassengerRepository(ObjectMapper objectMapper, @Value("${repository.filepath.passenger}") String filePath ) {
        super(filePath, objectMapper,  Passenger.class);
    }

    @Override
    protected String getIdFromEntity(Passenger entity) {
        return entity.getId();
    }
}
