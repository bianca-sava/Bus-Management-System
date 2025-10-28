package com.example.busmanagementsystem.model;

public class Bus {

    private String id;
    private String registrationNumber;
    int capacity;
    private BusStatus status;

    public Bus(String id, String registrationNumber, int capacity) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        status = BusStatus.DOWN;
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

    public String getStatus() {
        if (status == BusStatus.DOWN)
            return "Down";
        else
            return "Active";
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
