package com.example.busmanagementsystem.model;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "origin_station_id", nullable = false)
    private BusStation origin;
    @ManyToOne
    @JoinColumn(name = "destination_station_id", nullable = false)
    private BusStation destination;
    private double distance;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")
    private List<BusTrip> trips;
    private int nrOfStations;

    public Route() {}

    /// Constructor
    public Route(String id, BusStation origin, BusStation destination, double distance, int nrOfStations) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        trips = new ArrayList<BusTrip>();
        this.nrOfStations = nrOfStations;
    }

    /// Getters and Setters

//region
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BusStation getOrigin() {
        return origin;
    }

    public void setOrigin(BusStation origin) {
        this.origin = origin;
    }

    public BusStation getDestination() {
        return destination;
    }

    public void setDestination(BusStation destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<BusTrip> getTrips() {
        return trips;
    }

    public void setTrips(List<BusTrip> trips) {
        this.trips = trips;
    }

    public int getNrOfStations() {
        return nrOfStations;
    }

    public void setNrOfStations(int nrOfStations) {
        this.nrOfStations = nrOfStations;
    }
    //endregion

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", distance=" + distance +
                ", trips=" + trips +
                '}';
    }


}