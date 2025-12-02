package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.repository.interfaces.BusTripJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusTripDatabaseService {

    private final BusTripJpaRepository busTripRepository;

    @Autowired
    public BusTripDatabaseService(BusTripJpaRepository repository) {
        this.busTripRepository = repository;
    }

    public boolean create(BusTrip busTrip) {
        if (busTripRepository.existsById(busTrip.getId())) {
            throw new DuplicateAttributeException("id", "This ID already exists!");
        }
        return busTripRepository.save(busTrip) != null;
    }

    public boolean update(String id, BusTrip busTrip) {
        Optional<BusTrip> busTripOptional = busTripRepository.findById(id);

        if (busTripOptional.isPresent()) {
            BusTrip existing = busTripOptional.get();

            existing.setRouteId(busTrip.getRouteId());
            existing.setBusId(busTrip.getBusId());
            existing.setStartTime(busTrip.getStartTime());
            existing.setStatus(busTrip.getStatus());

            if (busTrip.getTickets() != null) existing.setTickets(busTrip.getTickets());
            if (busTrip.getAssignments() != null) existing.setAssignments(busTrip.getAssignments());

            busTripRepository.save(existing);
            return true;
        }
        return false;
    }

    public Map<String, BusTrip> findAll() {
        return busTripRepository.findAll().stream()
                .collect(Collectors.toMap(BusTrip::getId, trip -> trip));
    }

    public BusTrip findById(String id) {
        return busTripRepository.findById(id).orElse(null);
    }

    public boolean delete(String id) {
        if (busTripRepository.existsById(id)) {
            busTripRepository.deleteById(id);
            return true;
        }
        return false;
    }
}