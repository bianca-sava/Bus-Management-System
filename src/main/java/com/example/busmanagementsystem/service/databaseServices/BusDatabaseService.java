package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.model.Bus;

import com.example.busmanagementsystem.repository.interfaces.BusJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusDatabaseService {

    private BusJpaRepository busRepository;

    @Autowired
    public BusDatabaseService(BusJpaRepository repository) {
        this.busRepository = repository;
    }

    public boolean create (Bus bus) {
        return busRepository.save(bus) != null;
    }

    public Map<String ,Bus> findAll() {
        return busRepository.findAll().stream()
                .collect(Collectors.toMap(Bus::getId,
                        bus -> bus));
    }

    public Bus findById (String id) {
        return busRepository.findById(id).orElse(null);
    }

    public boolean update (String id, Bus bus) {
        Optional<Bus> optionalBus = busRepository.findById(id);
        if (optionalBus.isPresent()) {
            Bus existingBus = optionalBus.get();

            existingBus.setId(bus.getId());
            existingBus.setCapacity(bus.getCapacity());
            existingBus.setStatus(bus.getStatus());
            existingBus.setNrOfPassengers(bus.getNrOfPassengers());
            existingBus.setRegistrationNumber(bus.getRegistrationNumber());

            busRepository.save(existingBus);
            return true;
        }
        return false;
    }

    public boolean delete (String id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
