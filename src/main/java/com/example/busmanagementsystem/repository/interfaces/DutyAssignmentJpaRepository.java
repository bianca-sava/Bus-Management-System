package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.DutyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DutyAssignmentJpaRepository extends JpaRepository<DutyAssignment, String> {
}