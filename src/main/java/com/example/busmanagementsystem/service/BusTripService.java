package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.repository.BusTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BusTripService {

    private BusTripRepository repository;

    @Autowired
    public BusTripService(BusTripRepository repository) {
        this.repository = repository;
    }

    public boolean create (BusTrip busTrip) {
        return repository.create(busTrip);
    }

    public Map<String, BusTrip> findAll() {
        return repository.findAll();
    }

    public BusTrip findById (String id) {
        return repository.findById(id);
    }

    public boolean update (String id, BusTrip busTrip) {
        return repository.update(id, busTrip);
    }

    public boolean delete (String id) {
        return repository.delete(id);
    }
}

