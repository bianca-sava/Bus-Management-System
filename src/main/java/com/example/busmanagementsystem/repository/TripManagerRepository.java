package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.TripManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class TripManagerRepository extends InFileRepository<TripManager> {

    protected TripManagerRepository(ObjectMapper objectMapper, @Value("${repository.filepath.tripmanager}") String filePath ) {
        super(filePath, objectMapper,  TripManager.class);
    }

    @Override
    protected String getIdFromEntity(TripManager entity) {
        return entity.getId();
    }
}
