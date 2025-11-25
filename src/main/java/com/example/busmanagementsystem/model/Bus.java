package com.example.busmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.MessageInterpolator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@Entity
public class Bus {

    @Id
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Bus ID cannot be blank")
    private String id;
    @NotBlank(message = "Registration number cannot be blank")
    private String registrationNumber;
    @NotBlank(message = "Capacity cannot be blank")
    @Positive(message = "Capacity must be positive")
    @Size(min = 10, max = 100, message = "Capacity must be between 10 and 100")
    private int capacity;
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Status cannot be blank")
    private BusStatus status;
    @Positive(message = "Number of passengers must be positive")
    @NotBlank (message = "Number of passengers cannot be blank")
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
