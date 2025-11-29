package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.model.Route;
import com.example.busmanagementsystem.repository.interfaces.RouteJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteDatabaseService {

    private RouteJpaRepository routeRepository;

    @Autowired
    public RouteDatabaseService(RouteJpaRepository repository) {
        this.routeRepository = repository;
    }

    public boolean create (Route route) {
        return routeRepository.save(route) != null;
    }

    public Map<String, Route> findAll() {
        return routeRepository.findAll().stream()
                .collect(Collectors.toMap(Route::getId,
                route -> route));
    }

    public Route findById (String id) {
        return routeRepository.findById(id).orElse(null);
    }

    public boolean update (String id, Route route) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if (routeOptional.isPresent()) {
            Route existingRoute = routeOptional.get();

            existingRoute.setId(route.getId());
            existingRoute.setOrigin(route.getOrigin());
            existingRoute.setDestination(route.getDestination());
            existingRoute.setDistance(route.getDistance());
            existingRoute.setNrOfStations(route.getNrOfStations());
            existingRoute.setTrips(route.getTrips());

            routeRepository.save(existingRoute);
            return true;
        }
        return false;
    }

    public boolean delete (String id) {
        if (routeRepository.existsById(id)) {
            routeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
