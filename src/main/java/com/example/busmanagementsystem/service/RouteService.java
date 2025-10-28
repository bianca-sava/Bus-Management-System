package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.Route;
import com.example.busmanagementsystem.repository.RouteRepository;

import java.util.List;

public class RouteService {
    private RouteRepository repository;

    public RouteService() {
        this.repository = new RouteRepository();
    }

    public boolean create (Route route) {
        return repository.create(route);
    }

    public List<Route> findAll() {
        return repository.findAll();
    }

    public Route findById (String id) {
        return repository.findById(id);
    }

    public boolean update (String id, Route route) {
        return repository.update(id, route);
    }

    public boolean delete (String id) {
        return repository.delete(id);
    }
}
