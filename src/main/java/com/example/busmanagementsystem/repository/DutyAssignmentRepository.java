package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.model.Role;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DutyAssignmentRepository extends InMemoryRepository<DutyAssignment> {


    @Override
    protected String getIdFromEntity(DutyAssignment entity) {
        return entity.getId();
    }
}
