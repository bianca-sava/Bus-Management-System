package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException;
import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.model.BusTripStatus;
import com.example.busmanagementsystem.repository.interfaces.BusJpaRepository;
import com.example.busmanagementsystem.repository.interfaces.BusTripJpaRepository;
import com.example.busmanagementsystem.repository.interfaces.RouteJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusTripDatabaseService {

    private final BusTripJpaRepository busTripRepository;
    private final BusJpaRepository busJpaRepository;
    private final RouteJpaRepository routeJpaRepository;

    @Autowired
    public BusTripDatabaseService(BusTripJpaRepository repository, BusJpaRepository busJpaRepository, RouteJpaRepository routeJpaRepository) {
        this.busTripRepository = repository;
        this.busJpaRepository = busJpaRepository;
        this.routeJpaRepository = routeJpaRepository;
    }

    public boolean create(BusTrip busTrip) {
        if (busTripRepository.existsById(busTrip.getId())) {
            throw new DuplicateAttributeException("id", "This ID already exists!");
        }

        if (!busJpaRepository.existsById(busTrip.getBusId())) {
            throw new EntityNotFoundException("tripId", "The Trip with this ID does not exist!");
        }

        if (!routeJpaRepository.existsById(busTrip.getRouteId())) {
            throw new EntityNotFoundException("routeId", "The Route with this ID does not exist!");
        }

        return busTripRepository.save(busTrip) != null;
    }

    public boolean update(String id, BusTrip busTrip) {
        Optional<BusTrip> busTripOptional = busTripRepository.findById(id);

        if (!busJpaRepository.existsById(busTrip.getBusId())) {
            throw new EntityNotFoundException("tripId", "The Trip with this ID does not exist!");
        }

        if (!routeJpaRepository.existsById(busTrip.getRouteId())) {
            throw new EntityNotFoundException("routeId", "The Route with this ID does not exist!");
        }


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

    public Page<BusTrip> findAllPageable(
            String id,
            String routeId,
            String busId,
            BusTripStatus status,
            String minTime,
            String maxTime,
            Pageable pageable) {

        Sort.Order ticketCountOrder = pageable.getSort().getOrderFor("nrOfTickets");
        Sort.Order assignmentCountOrder = pageable.getSort().getOrderFor("nrOfAssignments");

        if (ticketCountOrder != null) {
            // Logica existentă pentru sortare după Tichete (Fără filtre)
            String sortAlias = "ticketCount";
            Sort newSort = Sort.by(ticketCountOrder.getDirection(), sortAlias);
            Pageable customPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
            Page<Object[]> result = busTripRepository.findAllSortedByTicketCount(customPageable);

            List<BusTrip> content = result.getContent().stream()
                    .map(obj -> (BusTrip) obj[0])
                    .collect(Collectors.toList());
            return new PageImpl<>(content, pageable, result.getTotalElements());

        } else if (assignmentCountOrder != null) {
            // Logica existentă pentru sortare după Assignments (Fără filtre)
            String sortAlias = "assignmentCount";
            Sort newSort = Sort.by(assignmentCountOrder.getDirection(), sortAlias);
            Pageable customPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);
            Page<Object[]> result = busTripRepository.findAllSortedByAssignmentCount(customPageable);

            List<BusTrip> content = result.getContent().stream()
                    .map(obj -> (BusTrip) obj[0])
                    .collect(Collectors.toList());
            return new PageImpl<>(content, pageable, result.getTotalElements());

        } else {
            return busTripRepository.findByFilters(
                    id, routeId, busId, status, minTime, maxTime, pageable
            );
        }
    }

}