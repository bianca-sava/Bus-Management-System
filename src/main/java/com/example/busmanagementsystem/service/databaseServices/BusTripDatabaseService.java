package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.repository.interfaces.BusTripJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusTripDatabaseService {

    private BusTripJpaRepository busTripRepository;

    @Autowired
    public BusTripDatabaseService(BusTripJpaRepository repository) {
        this.busTripRepository = repository;
    }

    public boolean create (BusTrip busTrip) {
        return busTripRepository.save(busTrip) != null;
    }

    public Map<String, BusTrip> findAll() {
        return busTripRepository.findAll().stream()
                .collect(Collectors.toMap(BusTrip::getId,
                        busTrip -> busTrip));
    }

    public BusTrip findById (String id) {
        return busTripRepository.findById(id).orElse(null);
    }

    public boolean update (String id, BusTrip busTrip) {
        Optional<BusTrip> busTripOptional = busTripRepository.findById(id);
        if (busTripOptional.isPresent()) {
            BusTrip existingBusTrip = busTripOptional.get();

            existingBusTrip.setId(busTrip.getId());
            existingBusTrip.setBusId(busTrip.getBusId());
            existingBusTrip.setAssignments(busTrip.getAssignments());
            existingBusTrip.setStatus(busTrip.getStatus());
            existingBusTrip.setTickets(busTrip.getTickets());
            existingBusTrip.setRouteId(busTrip.getRouteId());
            existingBusTrip.setStartTime(busTrip.getStartTime());

            busTripRepository.save(existingBusTrip);
            return true;
        }
        return false;
    }

    public boolean delete (String id) {
        if (busTripRepository.existsById(id)) {
            busTripRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
