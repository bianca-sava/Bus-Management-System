package com.example.busmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Positive;

@Entity
public class Ticket {
    @Id
    @NotBlank(message = "The Id can't be blank")
    private String id;

    @NotBlank(message = "The Trip can't be blank")
    @Column(name = "trip_id")
    private String tripId;

    @NotBlank(message = "The Passenger")
    @Column(name = "passenger_id")
    private String passengerId;

    @Pattern(regexp = "[1-9][0-9]?[A-C]")
    private String seatNumber;

    @Positive
    private double price;

    boolean checkedIn;


    public Ticket() {
        checkedIn = false;
    }

    public Ticket(String id, String tripId, String passengerId, String seatNumber, double price) {
        this.id = id;
        this.tripId = tripId;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
        this.price = price;
        checkedIn = false;
    }

    ///  Getters and Setters
    //region
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }
    //endregion


    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", tripId='" + tripId + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", price=" + price +
                '}';
    }

}
