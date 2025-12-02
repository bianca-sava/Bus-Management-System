package com.example.busmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Passenger {
    @Id
    @NotBlank(message = "Passenger ID cannot be blank")
    @Column(unique = true, nullable = false)
    private String id;

    @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "Name can only contain letters, spaces, apostrophes, and hyphens")
    @NotBlank(message = "Passenger name cannot be blank")
    private String name;

    @NotBlank(message = "Currency cannot be blank")
    @Pattern(regexp = "^(EUR|RON|USD)$",
            message = "Invalid currency! The accepted currencies are: EUR, RON and USD")
    private String currency;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", insertable = false, updatable = false)
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