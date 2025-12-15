package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.Passenger;
import com.example.busmanagementsystem.repository.interfaces.PassengerJpaRepository;
import com.example.busmanagementsystem.service.Validate;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassengerDatabaseService implements Validate<Passenger> {

    private PassengerJpaRepository passengerRepository;

    @Autowired
    public PassengerDatabaseService(PassengerJpaRepository repository) {
        this.passengerRepository = repository;
    }

    @Override
    public void validate(Passenger passenger) {
        if(passengerRepository.existsById(passenger.getId().trim())){
            throw new DuplicateAttributeException("id", "The passenger id has to be unique. The current id "
                    + passenger.getId().trim() + " is already used.");
        }
    }

    public boolean create (Passenger passenger) {
        validate(passenger);

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

            existingPassenger.setName(passenger.getName());
            existingPassenger.setCurrency(passenger.getCurrency());
            existingPassenger.setTickets(passenger.getTickets());

            passengerRepository.save(existingPassenger);
            return true;
        }
        return false;
    }

    public Page<Passenger> findAllPage(Pageable pageable) {
        Sort.Order tripCountOrder = pageable.getSort().getOrderFor("nrOfTickets");

        if (tripCountOrder != null) {

            String sortExpression = "ticketCount";

            Sort newSort = Sort.by(tripCountOrder.getDirection(), sortExpression);

            Pageable customPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);

            Page<Object[]> tripCountResult = passengerRepository.findAllSortedByTicketCount(customPageable);

            List<Passenger> content = tripCountResult.getContent().stream()
                    .map(obj -> (Passenger) obj[0])
                    .collect(Collectors.toList());

            return new PageImpl<>(content, pageable, tripCountResult.getTotalElements());

        } else {
            return passengerRepository.findAll(pageable);
        }
    }

    public boolean delete (String id) {
        if (passengerRepository.existsById(id)) {
            passengerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
