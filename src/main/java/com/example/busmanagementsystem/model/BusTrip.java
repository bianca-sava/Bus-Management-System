package com.example.busmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
@Entity
public class BusTrip {
    @Id
    @Column(unique = true, nullable = false)
    @NotBlank(message = "The ID can't be blank")
    private String id;

    @Column(name = "route_id", insertable = false, updatable = false, nullable = false, unique = true)
    @NotBlank(message = "The Route can't be blank")
    private String routeId;

    @NotBlank(message = "The Bus can't be blank")
    private String busId;

    @NotBlank(message = "The Time can't be blank")
    private String startTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private List<Ticket> tickets;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "duty_assignment_id")
    private List<DutyAssignment> assignments;

    @Enumerated(EnumType.STRING)
    private BusTripStatus status;

    public BusTrip() {}

    public BusTrip(String id, String routeId, String busId, String startTime, BusTripStatus status) {
        this.id = id;
        this.routeId = routeId;
        this.busId = busId;
        this.startTime = startTime;
        this.status = status;
        tickets = new ArrayList<Ticket>();
        assignments = new ArrayList<DutyAssignment>();
    }

    public BusTrip(String id, String routeId, String busId, String startTime) {
        this.id = id;
        this.routeId = routeId;
        this.busId = busId;
        this.startTime = startTime;
        status = BusTripStatus.PLANNED;
        tickets = new ArrayList<Ticket>();
        assignments = new ArrayList<DutyAssignment>();
    }

    /// Getters

//region
    public String getId() {
        return id;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getBusId() {
        return busId;
    }

    public String getStartTime() {
        return startTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public BusTripStatus getStatus() {
        return status;
    }

    public List<DutyAssignment> getAssignments() {
        return assignments;
    }
//endregion

    /// Setters

//region
    public void setId(String id) {
        this.id = id;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setAssignments(List<DutyAssignment> assignments) {
        this.assignments = assignments;
    }

    public void setStatus(BusTripStatus status) {
        this.status = status;
    }
//endregion


    @Override
    public String toString() {
        return "BusTrip{" +
                "id='" + id + '\'' +
                ", routeId='" + routeId + '\'' +
                ", busId='" + busId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", tickets=" + tickets +
                ", assignments=" + assignments +
                ", status=" + status +
                '}';
    }
}
