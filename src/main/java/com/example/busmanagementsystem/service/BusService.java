package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class BusService {
    private BusRepository repository;

    @Autowired
    public BusService(BusRepository repository) {
        this.repository = repository;
    }

    public boolean create (Bus bus) {
        return repository.create(bus);
    }

    public Map<String ,Bus> findAll() {
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
