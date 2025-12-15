package com.example.busmanagementsystem.service.inFileServices;


import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class TicketService {
    private TicketRepository repository;

    @Autowired
    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public boolean create (Ticket ticket) {
        return repository.create(ticket);
    }

    public Map<String, Ticket> findAll() {
        return repository.findAll();
    }

    public Ticket findById (String id) {
        return repository.findById(id);
    }

    public boolean update (String id, Ticket ticket) {
        return repository.update(id, ticket);
    }

    public boolean delete (String id) {
        return repository.delete(id);
    }
}
