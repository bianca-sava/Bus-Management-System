package com.example.busmanagementsystem.service;


import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.repository.TicketRepository;

import java.util.List;

public class TicketService {
    private TicketRepository repository;

    public TicketService() {
        this.repository = new TicketRepository();
    }

    public boolean create (Ticket ticket) {
        return repository.create(ticket);
    }

    public List<Ticket> findAll() {
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
