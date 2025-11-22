package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Bus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public class BusRepository extends  DatabaseRepository<Bus>{


    protected BusRepository(JpaRepository<Bus, String> jpaRepository) {
        super(jpaRepository);
    }

    @Override
    protected String getIdFromEntity(Bus entity) {
        return entity.getId();
    }

}