package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Bus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;


@Repository
public class BusRepository extends  InFileRepository<Bus>{

    protected BusRepository(ObjectMapper objectMapper) {
        super("/Users/biancasava/IdeaProjects/Bus Management System/src/main/resources/data/bus.json", objectMapper,  Bus.class);
    }

    @Override
    protected String getIdFromEntity(Bus entity) {
        return entity.getId();
    }

}