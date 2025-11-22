package com.example.busmanagementsystem.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BusStation {
    @Id
    private String id;
    private String name;
    private String city;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "station_trips_link", // Se va crea un tabel nou de legătură
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private List<BusTrip> trips = new ArrayList<>();

    /// Constructor
    public BusStation() {}
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