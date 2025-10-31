package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Bus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BusRepository extends  InMemoryRepository<Bus>{


    @Override
    protected String getIdFromEntity(Bus entity) {
        return entity.getId();
    }
}