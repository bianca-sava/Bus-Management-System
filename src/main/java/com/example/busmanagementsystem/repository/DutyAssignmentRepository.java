package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.DutyAssignment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DutyAssignmentRepository implements CRUD<DutyAssignment> {

    private List<DutyAssignment> assignments;

    public DutyAssignmentRepository() {

        assignments = new ArrayList<DutyAssignment>();
        DutyAssignment sampleAssignment = new DutyAssignment("DA001", "BT001", "2024-07-01");
        DutyAssignment sampleAssignment2 = new DutyAssignment("DA002", "BT002", "2024-07-02", Role.RESERVE_DRIVER);
        DutyAssignment sampleAssignment3 = new DutyAssignment("DA003", "BT003", "2024-07-03", Role.PRIMARY_DRIVER);
        assignments.add(sampleAssignment);
        assignments.add(sampleAssignment2);
        assignments.add(sampleAssignment3);
    }

    @Override
    public boolean create(DutyAssignment entity) {
        if(assignments.add(entity)){
            return true;
        }
        return false;
    }

    @Override
    public List<DutyAssignment> findAll() {
        return assignments;
    }

    @Override
    public DutyAssignment findById(String id) {
        for(DutyAssignment assignment : assignments){
            if(assignment.getId().equals(id)){
                return assignment;
            }
        }
        return null;
    }

    @Override
    public boolean update(String id, DutyAssignment entity) {
        for(int i = 0; i < assignments.size(); i++){
            if(assignments.get(i).getId().equals(id)){
                assignments.set(i, entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for(int i = 0; i < assignments.size(); i++){
            if(assignments.get(i).getId().equals(id)){
                assignments.remove(i);
                return true;
            }
        }
        return false;
    }
}
