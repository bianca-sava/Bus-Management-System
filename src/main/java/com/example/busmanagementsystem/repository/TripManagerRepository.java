package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.TripManager;
import org.springframework.stereotype.Repository;

@Repository
public class TripManagerRepository extends InMemoryRepository<TripManager> {

    @Override
    protected String getIdFromEntity(TripManager entity) {
        return entity.getId();
    }
}
