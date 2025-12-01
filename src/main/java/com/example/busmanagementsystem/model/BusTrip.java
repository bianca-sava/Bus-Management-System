package com.example.busmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BusTrip {

    @Id
    @Column(unique = true, nullable = false)
    @NotBlank(message = "ID-ul este obligatoriu")
    @Size(min = 3, max = 20, message = "ID-ul trebuie să aibă între 3 și 20 caractere")
    private String id;

    @NotBlank(message = "Trebuie să selectați o rută")
    @Column(name = "route_id")
    private String routeId;

    @NotBlank(message = "Trebuie să selectați un autobuz")
    private String busId;

    @NotBlank(message = "Ora de plecare este obligatorie")
    private String startTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id")
    private List<Ticket> tickets;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "duty_assignment_id")
    private List<DutyAssignment> assignments;

    @Enumerated(EnumType.STRING)
    private BusTripStatus status;

    public BusTrip() {
        this.status = BusTripStatus.PLANNED;
    }

    public BusTrip(String id, String routeId, String busId, String startTime) {
        this.id = id;
        this.routeId = routeId;
        this.busId = busId;
        this.startTime = startTime;
        this.status = BusTripStatus.PLANNED;
        this.tickets = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRouteId() { return routeId; }
    public void setRouteId(String routeId) { this.routeId = routeId; }

    public String getBusId() { return busId; }
    public void setBusId(String busId) { this.busId = busId; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public BusTripStatus getStatus() { return status; }
    public void setStatus(BusTripStatus status) { this.status = status; }

    public List<DutyAssignment> getAssignments() { return assignments; }
    public void setAssignments(List<DutyAssignment> assignments) { this.assignments = assignments; }
}