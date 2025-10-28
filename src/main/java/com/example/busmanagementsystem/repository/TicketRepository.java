package com.example.busmanagementsystem.repository;

import java.util.ArrayList;
import java.util.List;
import com.example.busmanagementsystem.model.Ticket;

public class TicketRepository implements CRUD<Ticket>{

    private List<Ticket> tickets;

    public TicketRepository() {
        tickets = new ArrayList<Ticket>();
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
