package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Passenger;

import java.util.ArrayList;
import java.util.List;

public class PassengerRepository implements CRUD<Passenger> {
    private List<Passenger> passengers;

    public PassengerRepository() {

        passengers = new ArrayList<Passenger>();
        Passenger passenger1 = new Passenger("P001", "Bob", "Leu");
        Passenger passenger2 = new Passenger("P002", "Alice", "Euro");
        Passenger passenger3 = new Passenger("P003", "Dob", "Leu");
        passengers.add(passenger1);
        passengers.add(passenger2);
        passengers.add(passenger3);
    }

    @Override
    public boolean create(Passenger entity) {
        if(passengers.add(entity)){
            return true;
        }
        return false;
    }

    @Override
    public List<Passenger> findAll() {
        return passengers;
    }

    @Override
    public Passenger findById(String id) {
        for(Passenger passenger : passengers){
            if(passenger.getId().equals(id)){
                return passenger;
            }
        }
        return null;
    }

    @Override
    public boolean update(String id, Passenger entity) {
        for(int i = 0; i < passengers.size(); i++){
            if(passengers.get(i).getId().equals(id)){
                passengers.set(i,entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for(int i = 0; i < passengers.size(); i++){
            if(passengers.get(i).getId().equals(id)){
                passengers.remove(i);
                return true;
            }
        }
        return false;
    }
}
