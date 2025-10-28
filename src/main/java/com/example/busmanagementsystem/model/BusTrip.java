package com.example.busmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class BusTrip {

    private String id;
    private String routeId;
    private String busId;
    private String startTime;
    private List<Ticket> tickets;
    private List<DutyAssignment> assignments;
    private BusTripStatus status;

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
