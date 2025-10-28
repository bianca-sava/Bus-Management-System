package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.repository.BusTripRepository;

import java.util.List;

public class BusTripService {
    private BusTripRepository repository;
    public BusTripService() {
        this.repository = new BusTripRepository();
    }

    public boolean create (BusTrip busTrip) {
        return repository.create(busTrip);
    }

    public List<BusTrip> findAll() {
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

