package com.example.busmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;


@Entity
public class Bus {

    @Id
    private String id;
    private String registrationNumber;
    private int capacity;
    @Enumerated(EnumType.STRING)
    private BusStatus status;
    private int nrOfPassengers;

    public Bus(){
        status = BusStatus.DOWN;
        nrOfPassengers = 0;
    }
    public Bus(String id, String registrationNumber, int capacity) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        status = BusStatus.DOWN;
        nrOfPassengers = 0;
    }

    public Bus(String id, String registrationNumber, int capacity,  BusStatus status, int  nrOfPassengers) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.status = status;
        this.nrOfPassengers = nrOfPassengers;
    }

    /// Getters

//region
    public String getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public BusStatus getStatus() {
        return status;
    }

    public int getNrOfPassengers() {
        return nrOfPassengers;
    }
    //endregion

    /// Setters

//region
    public void setId(String id) {
        this.id = id;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStatus(BusStatus status) {
        this.status = status;
    }

    public void setNrOfPassengers(int nrOfPassengers) {
        this.nrOfPassengers = nrOfPassengers;
    }
    //endregion


    @Override
    public String toString() {
        return "Bus{" +
                "id='" + id + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", capacity=" + capacity +
                ", status=" + status +
                '}';
    }


}
