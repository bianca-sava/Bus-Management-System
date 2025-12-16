package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.exceptions.DuplicateAttributeException;
import com.example.busmanagementsystem.exceptions.EntityNotFoundException;
import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.model.Role;
import com.example.busmanagementsystem.repository.interfaces.DriverJpaRepository;
import com.example.busmanagementsystem.repository.interfaces.BusTripJpaRepository;
import com.example.busmanagementsystem.repository.interfaces.DutyAssignmentJpaRepository;
import com.example.busmanagementsystem.service.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DutyAssignmentsDatabaseService implements Validate<DutyAssignment> {

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

    @Override
    public void validate(DutyAssignment assignment) throws RuntimeException {
        if (!tripRepository.existsById(assignment.getTripId())) {
            throw new EntityNotFoundException("tripId", "Călătoria cu acest ID nu există!");
        }
        if (assignment.getStaffId() != null) {
            if (!driverRepository.existsById(assignment.getStaffId())) {
                throw new EntityNotFoundException("staffId", "Șoferul cu acest ID nu există!");
            }
        }
    }

    private void normalizeAssignment(DutyAssignment assignment) {
        if (assignment.getStaffId() != null && assignment.getStaffId().trim().isEmpty()) {
            assignment.setStaffId(null);
        }
    }

    public boolean addAssignment(DutyAssignment assignment){
        if (assignmentRepository.existsById(assignment.getId())) {
            throw new DuplicateAttributeException("id", "Acest ID de asignare există deja!");
        }
        normalizeAssignment(assignment);
        validate(assignment);

        return assignmentRepository.save(assignment) != null;
    }

    public boolean updateAssignment(String id, DutyAssignment assignment){
        normalizeAssignment(assignment);
        validate(assignment);

        return assignmentRepository.findById(id).map(existing -> {
            existing.setRole(assignment.getRole());
            existing.setTripId(assignment.getTripId());
            existing.setStaffId(assignment.getStaffId()); // Poate fi null acum
            assignmentRepository.save(existing);
            return true;
        }).orElse(false);
    }


    public DutyAssignment getAssignmentById(String id){
        return assignmentRepository.findById(id).orElse(null);
    }

    public Page<DutyAssignment> findDutyAssignmentsByCriteria(String id, String tripId, String staffId, Role role, Pageable pageable){
        return assignmentRepository.findByFilters(id, tripId, staffId, role, pageable);
    }

    public Page<DutyAssignment> findAllAssignmentsPageable(String id, String tripId, String staffId, Role role, Pageable pageable){
        return findDutyAssignmentsByCriteria(id, tripId, staffId, role, pageable);
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

    public List<DutyAssignment> getUnassignedAssignments() {
        return assignmentRepository.findUnassignedDuties();
    }
}