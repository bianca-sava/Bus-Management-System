package com.example.busmanagementsystem.service;


import com.example.busmanagementsystem.model.Passenger;
import com.example.busmanagementsystem.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private PassengerRepository repository;

    @Autowired
    public PassengerService(PassengerRepository repository) {
        this.repository = repository;
    }

    public boolean create (Passenger passenger) {
        return repository.create(passenger);
    }

    public List<Passenger> findAll() {
        return repository.findAll();
    }

    public Passenger findById (String id) {
        return repository.findById(id);
    }

    public boolean update (String id, Passenger passenger) {
        return repository.update(id, passenger);
    }

    public boolean delete (String id) {
        return repository.delete(id);
    }
}
