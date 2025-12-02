package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.repository.interfaces.BusStationJpaRepository;
import com.example.busmanagementsystem.service.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusStationDatabaseService implements Validate<BusStation> {
    private BusStationJpaRepository busStationRepository;

    @Autowired
    public BusStationDatabaseService(BusStationJpaRepository repository) {
        this.busStationRepository = repository;
    }

    @Override
    public void validate(BusStation busStation) {
        if (busStationRepository.existsById(busStation.getId())) {
            throw new DuplicateAttributeException("id", "Acest ID de stație există deja!");
        }
    }

    public boolean create (BusStation busStation) {
        validate(busStation);

        return busStationRepository.save(busStation) != null;
    }

    public Map<String, BusStation> findAll() {
        return busStationRepository.findAll().stream()
                .collect(Collectors.toMap(BusStation::getId,
                station -> station));
    }

    public BusStation findById (String id) {
        return busStationRepository.findById(id).orElse(null);
    }

    public boolean update (String id, BusStation busStation) {
        Optional<BusStation> busStationOpt = busStationRepository.findById(id);

        if (busStationOpt.isPresent()) {
            BusStation existingStation = busStationOpt.get();
            existingStation.setName(busStation.getName());
            existingStation.setCity(busStation.getCity());
            existingStation.setTrips(busStation.getTrips().isEmpty() ? existingStation.getTrips() : busStation.getTrips());

            busStationRepository.save(existingStation);
            return true;
        }
        return false;
    }

    public boolean delete (String id) {
        if (busStationRepository.existsById(id)) {
            busStationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
