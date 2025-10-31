package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.repository.BusStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusStationService {
    private BusStationRepository repository;

    @Autowired
    public BusStationService(BusStationRepository repository) {
        this.repository = repository;
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
