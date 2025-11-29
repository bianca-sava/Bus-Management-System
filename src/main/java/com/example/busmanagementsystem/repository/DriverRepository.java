package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.Driver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DriverRepository extends InFileRepository<Driver>{

    protected DriverRepository(ObjectMapper objectMapper, @Value("${repository.filepath.driver}") String filePath ) {
        super(filePath, objectMapper,  Driver.class);
    }

    @Override
    protected String getIdFromEntity(Driver entity) {
        return entity.getId();
    }
}
