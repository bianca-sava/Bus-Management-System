package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Passenger;

import java.util.List;

public class PassengerRepository implements CRUD<Passenger> {
    private List<Passenger> passengers;

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
