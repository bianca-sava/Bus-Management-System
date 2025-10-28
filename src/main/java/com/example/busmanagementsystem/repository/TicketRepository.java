package com.example.busmanagementsystem.repository;

import java.util.ArrayList;
import java.util.List;
import com.example.busmanagementsystem.model.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository implements CRUD<Ticket>{

    private List<Ticket> tickets;

    public TicketRepository() {
        tickets = new ArrayList<>(List.of(
                new Ticket("T1", "TRIP1", "P1", "12A", 99.99),
                new Ticket("T2", "TRIP2", "P2", "7B", 79.50),
                new Ticket("T3", "TRIP3", "P3", "18C", 120.00)
        ));
    }

    @Override
    public boolean create(Ticket entity) {
        if(tickets.add(entity)){
            return true;
        }
        return false;
    }

    @Override
    public List<Ticket> findAll() {
        return tickets;
    }

    @Override
    public Ticket findById(String id) {
        for(Ticket ticket : tickets){
            if(ticket.getId().equals(id)){
                return ticket;
            }
        }
        return null;
    }

    @Override
    public boolean update(String id, Ticket entity) {
        for(int i = 0; i < tickets.size(); i++){
            if(tickets.get(i).getId().equals(id)){
                tickets.set(i, entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for(int i = 0; i < tickets.size(); i++){
            if(tickets.get(i).getId().equals(id)){
                tickets.remove(i);
                return true;
            }
        }
        return false;
    }
}
