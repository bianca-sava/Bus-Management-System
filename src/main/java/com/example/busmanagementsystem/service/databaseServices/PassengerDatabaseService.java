package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.model.Passenger;
import com.example.busmanagementsystem.repository.interfaces.PassengerJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassengerDatabaseService {

    private PassengerJpaRepository passengerRepository;

    @Autowired
    public PassengerDatabaseService(PassengerJpaRepository repository) {
        this.passengerRepository = repository;
    }

    public boolean create (Passenger passenger) {
        return passengerRepository.save(passenger) != null;
    }

    public Map<String, Passenger> findAll() {
        return passengerRepository.findAll().stream()
                .collect(Collectors.toMap(Passenger::getId,
                        passenger -> passenger));
    }

    public Passenger findById (String id) {
        return passengerRepository.findById(id).orElse(null);
    }

    public boolean update (String id, Passenger passenger) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);
        if (passengerOptional.isPresent()) {
            Passenger existingPassenger = passengerOptional.get();

            existingPassenger.setId(passenger.getId());
            existingPassenger.setName(passenger.getName());
            existingPassenger.setCurrency(passenger.getCurrency());
            existingPassenger.setTickets(passenger.getTickets());

            passengerRepository.save(existingPassenger);
            return true;
        }
        return false;
    }

    public boolean delete (String id) {
        if (passengerRepository.existsById(id)) {
            passengerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
