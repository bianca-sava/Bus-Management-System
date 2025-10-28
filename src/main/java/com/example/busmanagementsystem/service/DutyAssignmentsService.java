package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.repository.DutyAssignmentRepository;

import java.util.List;

public class DutyAssignmentsService {

    private DutyAssignmentRepository assignmentRepository;

    public  DutyAssignmentsService(DutyAssignmentRepository assignmentRepository){
        this.assignmentRepository = assignmentRepository;
    }

    public boolean addAssignment(DutyAssignment assignment){
        return assignmentRepository.create(assignment);
    }

    public DutyAssignment getAssignmentById (String id){
        return assignmentRepository.findById(id);
    }

    public List<DutyAssignment> getAllAssignments(){
        return assignmentRepository.findAll();
    }

    public boolean updateAssignment(String id, DutyAssignment assignment){
        return assignmentRepository.update(id, assignment);
    }

    public boolean deleteAssignment(String id){
        return assignmentRepository.delete(id);
    }
}
