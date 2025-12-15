package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.TripManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TripManagerJpaRepository extends JpaRepository<TripManager, String> {
    boolean existsByEmployeeCode(String employeeCode);

    @Query(value = "SELECT tm, COUNT(a) AS assignmentCount FROM TripManager tm LEFT JOIN tm.assignments a GROUP BY tm",
            countQuery = "SELECT COUNT(tm) FROM TripManager tm",
            nativeQuery = false)
    Page<Object[]> findAllSortedByAssignmentCount(Pageable pageable);
}