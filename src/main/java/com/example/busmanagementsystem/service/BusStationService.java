package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.repository.BusStationRepository;

import java.util.List;

public class BusStationService {
    private BusStationRepository repository;
    public BusStationService() {
        this.repository = new BusStationRepository();
    }

    public boolean create (BusStation busStation) {
        return repository.create(busStation);
    }

    public List<BusStation> findAll() {
        return repository.findAll();
    }

    public BusStation findById (String id) {
        return repository.findById(id);
    }

    public boolean update (String id, BusStation busStation) {
        return repository.update(id, busStation);
    }

    public boolean delete (String id) {
        return repository.delete(id);
    }


}
