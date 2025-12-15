package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.Ticket;
import com.example.busmanagementsystem.model.TripManager;
import com.example.busmanagementsystem.repository.interfaces.TripManagerJpaRepository;
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
public class TripManagerDatabaseService implements Validate<TripManager> {

    private final TripManagerJpaRepository tripManagerRepository;

    @Autowired
    public TripManagerDatabaseService(TripManagerJpaRepository tripManagerRepository) {
        this.tripManagerRepository = tripManagerRepository;
    }

    @Override
    public void validate(TripManager tripManager) {
        if(tripManagerRepository.existsByEmployeeCode(tripManager.getEmployeeCode().trim())){
            throw new DuplicateAttributeException("employeeCode", "The employee code has to be unique. The code"
                    + tripManager.getEmployeeCode() + " is already used");
        }
    }

    @Transactional
    public boolean addTripManager(TripManager tripManager){
        if(tripManagerRepository.existsById(tripManager.getId().trim())){
            throw new DuplicateAttributeException("id", "The trip manager id has to be unique. The id"
                    + tripManager.getId() + " is already used");
        }

        validate(tripManager);

        return tripManagerRepository.save(tripManager) != null;
    }

    public TripManager getTripManagerById(String id){
        return tripManagerRepository.findById(id).orElse(null);
    }

    public Map<String, TripManager> findAllTripManagers(){
        return tripManagerRepository.findAll().stream()
                .collect(Collectors.toMap(
                        TripManager::getId,
                        manager -> manager));
    }

    @Transactional
    public boolean updateTripManager(String id, TripManager tripManager){
        Optional<TripManager> existingOpt = tripManagerRepository.findById(id);
        if(existingOpt.isPresent()){
            TripManager existingTripManager = existingOpt.get();

            if(!existingTripManager.getEmployeeCode().equals(tripManager.getEmployeeCode())){
                validate(tripManager);
            }

            existingTripManager.setName(tripManager.getName());
            existingTripManager.setEmployeeCode(tripManager.getEmployeeCode());
            existingTripManager.setAssignments(tripManager.getAssignments());

            tripManagerRepository.save(existingTripManager);
            return true;
        }
        return false;
    }

    public Page<TripManager> findAllPageable(Pageable pageable) {
        return tripManagerRepository.findAll(pageable);
    }

    public boolean deleteTripManager(String id){
        if(tripManagerRepository.existsById(id)){
            tripManagerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
