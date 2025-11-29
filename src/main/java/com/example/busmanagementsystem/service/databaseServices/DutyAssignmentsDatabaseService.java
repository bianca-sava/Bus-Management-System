package com.example.busmanagementsystem.service.databaseServices;

import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.repository.DutyAssignmentRepository;
import com.example.busmanagementsystem.repository.interfaces.DutyAssignmentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DutyAssignmentsDatabaseService {

    private DutyAssignmentJpaRepository assignmentRepository;

    @Autowired
    public  DutyAssignmentsDatabaseService(DutyAssignmentJpaRepository assignmentRepository){
        this.assignmentRepository = assignmentRepository;
    }

    public boolean addAssignment(DutyAssignment assignment){
        return assignmentRepository.save(assignment) != null;
    }

    public DutyAssignment getAssignmentById (String id){
        return assignmentRepository.findById(id).orElse(null);
    }

    public Map<String, DutyAssignment> getAllAssignments(){
        return assignmentRepository.findAll().stream()
                .collect(Collectors.toMap(DutyAssignment::getId,
                assignment -> assignment));
    }

    public boolean updateAssignment(String id, DutyAssignment assignment){
        Optional<DutyAssignment> optionalDutyAssignment = assignmentRepository.findById(id);
        if(optionalDutyAssignment.isPresent()){
            DutyAssignment existingDutyAssignment = optionalDutyAssignment.get();

            existingDutyAssignment.setId(assignment.getId());
            existingDutyAssignment.setRole(assignment.getRole());
            existingDutyAssignment.setTripId(assignment.getTripId());
            existingDutyAssignment.setStaffId(assignment.getStaffId());

            assignmentRepository.save(existingDutyAssignment);
            return true;
        }
        return false;
    }

    public boolean deleteAssignment(String id){
        if(assignmentRepository.existsById(id)){
            assignmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
