package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.DutyAssignment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public class DutyAssignmentRepository extends InFileRepository<DutyAssignment> {

    @Autowired
    public DutyAssignmentRepository(
            ObjectMapper objectMapper,
            @Value("${repository.filepath.dutyassignment}") String filePath
    ) {
        super(filePath, objectMapper, DutyAssignment.class);
    }

    @Override
    protected String getIdFromEntity(DutyAssignment entity) {
        return entity.getId();
    }
}
