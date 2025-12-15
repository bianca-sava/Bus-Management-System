package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException;
import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.repository.interfaces.BusTripJpaRepository;
import com.example.busmanagementsystem.repository.interfaces.PassengerJpaRepository;
import com.example.busmanagementsystem.repository.interfaces.TicketJpaRepository;
import com.example.busmanagementsystem.service.Validate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketDatabaseService implements Validate<Ticket> {
    private final TicketJpaRepository ticketRepository;
    private final BusTripJpaRepository busTripRepository;
    private final PassengerJpaRepository passengerRepository;

    @Autowired
    public TicketDatabaseService(TicketJpaRepository repository,
                                 BusTripJpaRepository busTripRepository,
                                 PassengerJpaRepository passengerRepository) {
        this.ticketRepository = repository;
        this.busTripRepository = busTripRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public void validate(Ticket ticket) throws RuntimeException {
        if(!busTripRepository.existsById(ticket.getTripId().trim())) {
            throw new EntityNotFoundException("tripId", "There is no Trip with the ID: "
                    + ticket.getTripId().trim());
        }
        if(!passengerRepository.existsById(ticket.getPassengerId().trim())) {
            throw new EntityNotFoundException("passenger",  "There is no Passenger with the ID: "
                    + ticket.getPassengerId().trim());
        }
    }

    @Transactional
    public boolean create (Ticket ticket) {
        if(ticketRepository.existsById(ticket.getId().trim())) {
            throw new DuplicateAttributeException("id", "The ticket id has to be unique. The id"
                    + ticket.getId() + " is already used");
        }

        validate(ticket);

        return ticketRepository.save(ticket) != null;
    }

    public Map<String, Ticket> findAll() {
        return ticketRepository.findAll().stream()
                .collect(Collectors.toMap(Ticket::getId,
                        ticket -> ticket));
    }

    public Ticket findById (String id) {
        return ticketRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean update (String id, Ticket ticket) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if(ticketOptional.isPresent()) {
            Ticket existingTicket = ticketOptional.get();

            existingTicket.setPrice(ticket.getPrice());
            existingTicket.setCheckedIn(ticket.isCheckedIn());
            existingTicket.setPassengerId(ticket.getPassengerId());
            existingTicket.setTripId(ticket.getTripId());
            existingTicket.setSeatNumber(ticket.getSeatNumber());

            validate(ticket);

            ticketRepository.save(existingTicket);
            return true;
        }
        return false;
    }

    public Page<Ticket> findTicketsByCriteria(
            String id,
            String tripId,
            String passengerId,
            String seatNumber,
            Double minPrice,
            Double maxPrice,
            Boolean checkedIn,
            Pageable pageable) {

        return ticketRepository.findByFilters(
                id,
                tripId,
                passengerId,
                seatNumber,
                minPrice,
                maxPrice,
                checkedIn,
                pageable
        );
    }
    public boolean delete (String id) {
        if(ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
