package com.example.busmanagementsystem.model;

public class Bus {

    private String id;
    private String registrationNumber;
    private int capacity;
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
