package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.DutyAssignment;
import com.example.busmanagementsystem.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DutyAssignmentJpaRepository extends JpaRepository<DutyAssignment, String> {
    @Query("SELECT d FROM DutyAssignment d WHERE " +
            "(:id IS NULL OR LOWER(d.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND " +
            "(:tripId IS NULL OR LOWER(d.tripId) LIKE LOWER(CONCAT('%', :tripId, '%'))) AND " +
            "(:staffId IS NULL OR LOWER(d.staffId) LIKE LOWER(CONCAT('%', :staffId, '%'))) AND " +
            "(:role IS NULL OR d.role = :role)")
    Page<DutyAssignment> findByFilters(
            @Param("id") String id,
            @Param("tripId") String tripId,
            @Param("staffId") String staffId,
            @Param("role") Role role,
            Pageable pageable
    );
}