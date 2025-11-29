package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.repository.interfaces.TicketJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends InFileRepository<Ticket> {

    protected TicketRepository(ObjectMapper objectMapper, @Value("${repository.filepath.ticket}") String filePath ) {
        super(filePath, objectMapper,  Ticket.class);
    }

    @Override
    protected String getIdFromEntity(Ticket entity) {
        return entity.getId();
    }
}
