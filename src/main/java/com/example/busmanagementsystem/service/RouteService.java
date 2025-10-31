package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.Route;
import com.example.busmanagementsystem.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class RouteService {
    private RouteRepository repository;

    @Autowired
    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

    public boolean create (Route route) {
        return repository.create(route);
    }

    public Map<String, Route> findAll() {
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
