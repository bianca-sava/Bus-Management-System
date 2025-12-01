package com.example.busmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "bus")
public class Bus {

    @Id
    @NotBlank(message = "The Id can't be blank")
    private String id;

    @NotBlank(message = "Registration number cannot be blank")
    @Pattern(regexp = "^[A-Z0-9-]{1,10}$", message = "Max 10 chars, alphanumeric only")
    @Column(unique = true)
    private String registrationNumber;

    @Positive(message = "Capacity must be positive")
    @Min(value = 10, message = "Capacity must be at least 10")
    @Max(value = 100, message = "Capacity must be at most 100")
    private int capacity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private BusStatus status;

    @PositiveOrZero(message = "Passengers cannot be negative")
    private int nrOfPassengers;

    public Bus(){
        this.status = BusStatus.DOWN;
        this.nrOfPassengers = 0;
    }

    public Bus(String id, String registrationNumber, int capacity,  BusStatus status, int  nrOfPassengers) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.status = status;
        this.nrOfPassengers = nrOfPassengers;
    }

    public Bus(String registrationNumber, int capacity) {
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.status = BusStatus.DOWN;
        this.nrOfPassengers = 0;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public BusStatus getStatus() { return status; }
    public void setStatus(BusStatus status) { this.status = status; }

    public int getNrOfPassengers() { return nrOfPassengers; }
    public void setNrOfPassengers(int nrOfPassengers) { this.nrOfPassengers = nrOfPassengers; }
}