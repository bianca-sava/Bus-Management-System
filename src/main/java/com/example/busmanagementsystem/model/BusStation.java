package com.example.busmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class BusStation {
    @Id
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Bus Station ID cannot be blank")
    private String id;
    @NotBlank(message = "Bus Station name cannot be blank")
    private String name;
    @NotBlank(message = "City cannot be blank")
    private String city;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_trip_id")
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