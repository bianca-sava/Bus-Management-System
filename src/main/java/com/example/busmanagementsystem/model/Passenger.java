package com.example.busmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Passenger {
    private String id;
    private String name;
    private String currency;
    private List<Ticket> tickets;

    /// Constructor
    public Passenger() {
        this.tickets = new ArrayList<Ticket>();
    }

    public Passenger(String id, String name, String currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        tickets = new ArrayList<Ticket>();
    }

    /// Getters and Setters
    //region

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
    //endregion


    @Override
    public String toString() {
        return "Pasenger{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", tickets=" + tickets +
                '}';
    }

}