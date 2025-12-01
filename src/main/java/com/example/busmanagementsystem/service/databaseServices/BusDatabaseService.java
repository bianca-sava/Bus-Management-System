package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.repository.interfaces.BusJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusDatabaseService {

    private final BusJpaRepository busRepository;

    @Autowired
    public BusDatabaseService(BusJpaRepository repository) {
        this.busRepository = repository;
    }

    public Map<String, Bus> findAll() {
        return busRepository.findAll().stream()
                .collect(Collectors.toMap(Bus::getId, bus -> bus));
    }

    public Bus findById(String id) {
        return busRepository.findById(id).orElse(null);
    }

    public void save(Bus bus) {
        if (bus.getNrOfPassengers() > bus.getCapacity()) {
            throw new IllegalArgumentException("Number of passengers cannot exceed capacity!");
        }

        Optional<Bus> existing = busRepository.findByRegistrationNumber(bus.getRegistrationNumber());
        if (existing.isPresent()) {
            if (bus.getId() == null || !existing.get().getId().equals(bus.getId())) {
                throw new DuplicateAttributeException("registrationNumber", "Registration number already exists!");
            }
        }

        busRepository.save(bus);
    }

    public void delete(String id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
        }
    }
}