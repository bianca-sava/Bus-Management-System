package com.example.busmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    @NotBlank(message= "The Id can't be blank")
    @Column(unique = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "origin_station_id", nullable = false)
    @NotNull(message = "The Beginning of the route can't be blank")
    private BusStation origin;

    @ManyToOne
    @JoinColumn(name = "destination_station_id", nullable = false)
    @NotNull(message = "The Destination of te route can't be blank")
    private BusStation destination;

    @Positive
    @Size(min = 2, max = 50)
    private double distance;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")
    private List<BusTrip> trips;

    @Positive
    @Size(min = 1, max = 20)
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