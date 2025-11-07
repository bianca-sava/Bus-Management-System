package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Driver;
import org.springframework.stereotype.Repository;

@Repository
public class DriverRepository extends InMemoryRepository<Driver>{

    @Override
    protected String getIdFromEntity(Driver entity) {
        return entity.getId();
    }
}
