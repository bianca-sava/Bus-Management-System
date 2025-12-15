package com.example.busmanagementsystem.repository.interfaces;

import com.example.busmanagementsystem.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverJpaRepository extends JpaRepository<Driver, String> {

    @Query(value = "SELECT d, COUNT(a) AS assignmentCount FROM Driver d LEFT JOIN d.assignments a GROUP BY d",
            countQuery = "SELECT COUNT(d) FROM Driver d",
            nativeQuery = false)
    Page<Object[]> findAllSortedByAssignmentCount(Pageable pageable);

}

