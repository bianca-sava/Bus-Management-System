package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.model.TripManager;
import com.example.busmanagementsystem.repository.interfaces.TripManagerJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripManagerDatabaseService {

    private final TripManagerJpaRepository tripManagerRepository;

    @Autowired
    public TripManagerDatabaseService(TripManagerJpaRepository tripManagerRepository) {
        this.tripManagerRepository = tripManagerRepository;
    }

    @Transactional
    public boolean addTripManager(TripManager tripManager){
        if(tripManagerRepository.existsByEmployeeCode(tripManager.getEmployeeCode())){
            throw new DuplicateAttributeException("employeeCode", "The employee code has to be unique. The code"
                    + tripManager.getEmployeeCode() + " is already used");
        }
        if(tripManagerRepository.existsById(tripManager.getId().trim())){
            throw new DuplicateAttributeException("id", "The trip manager id has to be unique. The id"
                    + tripManager.getId() + " is already used");
        }
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
            existingTripManager.setId(tripManager.getId());
            existingTripManager.setName(tripManager.getName());
            existingTripManager.setEmployeeCode(tripManager.getEmployeeCode());
            existingTripManager.setAssignments(tripManager.getAssignments());

            if(tripManagerRepository.existsByEmployeeCode(tripManager.getEmployeeCode())){
                throw new DuplicateAttributeException("employeeCode", "The employee code has to be unique. The code"
                        + tripManager.getEmployeeCode() + " is already used");
            }
            if(tripManagerRepository.existsById(tripManager.getId().trim())){
                throw new DuplicateAttributeException("id", "The trip manager id has to be unique. The id"
                        + tripManager.getId() + " is already used");
            }
            tripManagerRepository.save(existingTripManager);
            return true;
        }
        return false;
    }

    public boolean deleteTripManager(String id){
        if(tripManagerRepository.existsById(id)){
            tripManagerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
