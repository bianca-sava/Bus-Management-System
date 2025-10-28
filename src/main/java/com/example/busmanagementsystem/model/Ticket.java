package com.example.busmanagementsystem.model;

public class Ticket {
    private String id;
    private String tripId;
    private String passengerId;
    private String seatNumber;
    private double price;
    boolean isCheckedIn;

    public Ticket(String id, String tripId, String passengerId, String seatNumber, double price) {
        this.id = id;
        this.tripId = tripId;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
        this.price = price;
        isCheckedIn = false;
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
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
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
