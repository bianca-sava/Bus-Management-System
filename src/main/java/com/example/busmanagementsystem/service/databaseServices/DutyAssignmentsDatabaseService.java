package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException; // Asigură-te că ai excepția asta
import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.repository.interfaces.DriverJpaRepository; // Sau StaffJpaRepository
import com.example.busmanagementsystem.repository.interfaces.BusTripJpaRepository;
import com.example.busmanagementsystem.repository.interfaces.DutyAssignmentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DutyAssignmentsDatabaseService {

    private final DutyAssignmentJpaRepository assignmentRepository;
    private final BusTripJpaRepository tripRepository;
    private final DriverJpaRepository driverRepository;

    @Autowired
    public DutyAssignmentsDatabaseService(DutyAssignmentJpaRepository assignmentRepository,
                                          BusTripJpaRepository tripRepository,
                                          DriverJpaRepository driverRepository) {
        this.assignmentRepository = assignmentRepository;
        this.tripRepository = tripRepository;
        this.driverRepository = driverRepository;
    }

    public boolean addAssignment(DutyAssignment assignment){
        if (assignmentRepository.existsById(assignment.getId())) {
            throw new DuplicateAttributeException("id", "This ID already exists!");
        }

        if (!tripRepository.existsById(assignment.getTripId())) {
            throw new EntityNotFoundException("tripId", "The Trip with this ID does not exist!");
        }

        if (!driverRepository.existsById(assignment.getStaffId())) {
            throw new EntityNotFoundException("staffId", "The Driver with this ID does not exist!");
        }

        return assignmentRepository.save(assignment) != null;
    }

    public boolean updateAssignment(String id, DutyAssignment assignment){
        if (!tripRepository.existsById(assignment.getTripId())) {
            throw new EntityNotFoundException("tripId", "The Trip with this ID does not exist!");
        }
        if (!driverRepository.existsById(assignment.getStaffId())) {
            throw new EntityNotFoundException("staffId", "The Driver with this ID does not exist!");
        }

        return assignmentRepository.findById(id).map(existing -> {
            existing.setRole(assignment.getRole());
            existing.setTripId(assignment.getTripId());
            existing.setStaffId(assignment.getStaffId());
            assignmentRepository.save(existing);
            return true;
        }).orElse(false);
    }

    public DutyAssignment getAssignmentById(String id){
        return assignmentRepository.findById(id).orElse(null);
    }

    public Map<String, DutyAssignment> getAllAssignments(){
        return assignmentRepository.findAll().stream()
                .collect(Collectors.toMap(DutyAssignment::getId, a -> a));
    }

    public boolean deleteAssignment(String id){
        if(assignmentRepository.existsById(id)){
            assignmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}