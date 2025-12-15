package com.example.busmanagementsystem.repository.interfaces;

import com.example.busmanagementsystem.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverJpaRepository extends JpaRepository<Driver, String> {

    @Query(value = "SELECT d, COUNT(a) AS assignmentCount FROM Driver d LEFT JOIN d.assignments a GROUP BY d",
            countQuery = "SELECT COUNT(d) FROM Driver d",
            nativeQuery = false)
    Page<Object[]> findAllSortedByAssignmentCount(Pageable pageable);

    @Query("SELECT d FROM Driver d WHERE " +
            "(:id IS NULL OR LOWER(d.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND " +
            "(:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:minYears IS NULL OR d.yearsOfExperience >= :minYears) AND " +
            "(:maxYears IS NULL OR d.yearsOfExperience <= :maxYears)")
    Page<Driver> findByFilters(
            @Param("id") String id,
            @Param("name") String name,
            @Param("minYears") String minYears,
            @Param("maxYears") String maxYears,
            Pageable pageable
    );

}

