package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.DutyAssignment;
import org.springframework.stereotype.Repository;


@Repository
public class DutyAssignmentRepository extends InMemoryRepository<DutyAssignment> {

    @Override
    protected String getIdFromEntity(DutyAssignment entity) {
        return entity.getId();
    }
}
