package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.repository.interfaces.BusJpaRepository;
import com.example.busmanagementsystem.service.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusDatabaseService implements Validate<Bus> {

    private final BusJpaRepository busRepository;

    @Autowired
    public BusDatabaseService(BusJpaRepository repository) {
        this.busRepository = repository;
    }

    @Override
    public void validate(Bus bus) throws RuntimeException {
        if (bus.getNrOfPassengers() > bus.getCapacity()) {
            throw new IllegalArgumentException("Number of passengers cannot exceed capacity!");
        }
    }

    public Map<String, Bus> findAll() {
        return busRepository.findAll().stream()
                .collect(Collectors.toMap(Bus::getId, bus -> bus));
    }

    public Bus findById(String id) {
        return busRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean create(Bus bus){
        if(busRepository.existsById(bus.getId().trim())){
            throw new DuplicateAttributeException("id", "The id already exists!");
        }

        if(busRepository.existsByRegistrationNumber(bus.getRegistrationNumber().trim())){
            throw new DuplicateAttributeException("registrationNumber", "The registration number already exists!");
        }

        validate(bus);

        return busRepository.save(bus) != null;
    }

    @Transactional
    public boolean update(String id, Bus bus){
        Optional<Bus> busOptional = busRepository.findById(id);
        if(busOptional.isPresent()){
            Bus existingBus = busOptional.get();

            String newRegNum = bus.getRegistrationNumber().trim();
            if (!existingBus.getRegistrationNumber().equals(newRegNum)) {
                if (busRepository.existsByRegistrationNumber(newRegNum)) {
                    throw new DuplicateAttributeException("registrationNumber", "The registration number already exists!");
                }
            }

            existingBus.setRegistrationNumber(newRegNum);
            existingBus.setCapacity(bus.getCapacity());
            existingBus.setStatus(bus.getStatus());
            existingBus.setNrOfPassengers(bus.getNrOfPassengers());

            validate(bus);

            busRepository.save(existingBus);
            return true;
        }
        return false;
    }

    public Page<Bus> findAllPageable(Pageable pageable) {
        return busRepository.findAll(pageable);
    }

    public void delete(String id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
        }
    }
}