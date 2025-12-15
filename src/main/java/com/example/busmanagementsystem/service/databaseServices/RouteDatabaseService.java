package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException;
import com.example.busmanagementsystem.exceptions.InvalidRouteException;
import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.model.Route;
import com.example.busmanagementsystem.repository.interfaces.BusStationJpaRepository;
import com.example.busmanagementsystem.repository.interfaces.RouteJpaRepository;
import com.example.busmanagementsystem.service.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteDatabaseService implements Validate<Route> {

    private final RouteJpaRepository routeRepository;
    private final BusStationJpaRepository busStationRepository;


    @Autowired
    public RouteDatabaseService(RouteJpaRepository repository, BusStationJpaRepository busStationRepository) {
        this.routeRepository = repository;
        this.busStationRepository = busStationRepository;
    }

    @Override
    public void validate(Route route) throws RuntimeException {
        if (route.getOrigin().getId().equals(route.getDestination().getId())) {
            throw new InvalidRouteException(route);
        }
        if(!busStationRepository.existsById(route.getOrigin().getId().trim()) ||
                !busStationRepository.existsById(route.getDestination().getId().trim())) {
            throw new EntityNotFoundException("busStation", "The route origin or route destination don't exist.");
        }
    }

    public boolean create (Route route) {
        if(routeRepository.existsById(route.getId().trim())){
            throw new DuplicateAttributeException("id", "The route id has to be unique. The id "
                    + route.getId() + " is already used.");
        }
        validate(route);
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

            existingRoute.setOrigin(route.getOrigin());
            existingRoute.setDestination(route.getDestination());
            existingRoute.setDistance(route.getDistance());
            existingRoute.setNrOfStations(route.getNrOfStations());
            existingRoute.setTrips(route.getTrips());

            validate(route);
            routeRepository.save(existingRoute);
            return true;
        }
        return false;
    }

    public Page<Route> findRoutesByCriteria(
            String id,
            String originName,
            String destinationName,
            Double minDistance,
            Double maxDistance,
            Integer minStations,
            Integer maxStations,
            Integer minTrips,
            Integer maxTrips,
            Pageable pageable) {

        Sort.Order tripOrder = pageable.getSort().getOrderFor("nrOfTrips");
        Pageable activePageable = pageable;

        if (tripOrder != null) {
            Sort customSort = JpaSort.unsafe(tripOrder.getDirection(), "SIZE(r.trips)");
            activePageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), customSort);
        }

        return routeRepository.findByFilters(
                id, originName, destinationName, minDistance, maxDistance,
                minStations, maxStations,
                minTrips, maxTrips, activePageable
        );
    }

    public boolean delete (String id) {
        if (routeRepository.existsById(id)) {
            routeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
