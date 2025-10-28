package com.example.busmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class BusStation {
    private String id;
    private String name;
    private String city;
    private List<BusTrip> trips;

    /// Constructor
    public BusStation(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        return "BusStation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", trips=" + trips +
                '}';
    }

}