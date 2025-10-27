package com.example.busmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private String id;
    private BusStation origin;
    private BusStation destination;
    private double distance;
    private List<BusTrip> trips;

    /// Constructor
    public Route(String id, BusStation origin, BusStation destination, double distance) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        trips = new ArrayList<BusTrip>();
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