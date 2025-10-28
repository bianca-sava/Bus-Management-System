package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.repository.BusRepository;


import java.util.List;

public class BusService {
    private BusRepository repository;
    public BusService() {
        this.repository = new BusRepository();
    }

    public boolean create (Bus bus) {
        return repository.create(bus);
    }

    public List<Bus> findAll() {
        return repository.findAll();
    }

    public Bus findById (String id) {
        return repository.findById(id);
    }

    public boolean update (String id, Bus bus) {
        return repository.update(id, bus);
    }

    public boolean delete (String id) {
        return repository.delete(id);
    }
}
