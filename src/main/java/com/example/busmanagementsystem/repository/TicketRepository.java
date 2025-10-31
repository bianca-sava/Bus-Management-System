package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends InMemoryRepository<Ticket>{

    @Override
    protected String getIdFromEntity(Ticket entity) {
        return entity.getId();
    }
}
