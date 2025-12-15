package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.BusStation;
import com.example.busmanagementsystem.repository.interfaces.BusStationJpaRepository;
import com.example.busmanagementsystem.service.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
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
            throw new DuplicateAttributeException("id", "This ID already exists!");
        }
    }

    public boolean create (BusStation busStation) {
        validate(busStation);

        return busStationRepository.save(busStation) != null;
    }

    public Page<BusStation> findAllPageable(Pageable pageable) {

        Sort.Order tripCountOrder = pageable.getSort().getOrderFor("nrOfTrips");

        if (tripCountOrder != null) {

            String sortExpression = "tripCount";

            Sort newSort = Sort.by(tripCountOrder.getDirection(), sortExpression);

            Pageable customPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), newSort);

            Page<Object[]> tripCountResult = busStationRepository.findAllSortedByTripCount(customPageable);

            List<BusStation> content = tripCountResult.getContent().stream()
                    .map(obj -> (BusStation) obj[0])
                    .collect(Collectors.toList());

            return new PageImpl<>(content, pageable, tripCountResult.getTotalElements());

        } else {
            return busStationRepository.findAll(pageable);
        }
    }

    public Map<String, BusStation> findAll() {
        return busStationRepository.findAll().stream()
                .collect(Collectors.toMap(BusStation::getId, station -> station));
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
