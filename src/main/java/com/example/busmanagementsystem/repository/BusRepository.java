package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Bus;
import org.springframework.stereotype.Repository;


@Repository
public class BusRepository extends  InMemoryRepository<Bus>{

    @Override
    protected String getIdFromEntity(Bus entity) {
        return entity.getId();
    }
}