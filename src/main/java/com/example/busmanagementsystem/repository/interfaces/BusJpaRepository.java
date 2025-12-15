package com.example.busmanagementsystem.repository.interfaces;

import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.BusStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusJpaRepository extends JpaRepository<Bus, String> {
    boolean existsByRegistrationNumber(String registrationNumber);

    @Query("SELECT b FROM Bus b WHERE " +
            "(:id IS NULL OR LOWER(b.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND " +
            "(:regNum IS NULL OR LOWER(b.registrationNumber) LIKE LOWER(CONCAT('%', :regNum, '%'))) AND " +
            "(:status IS NULL OR b.status = :status) AND " +
            "(:minCap IS NULL OR b.capacity >= :minCap) AND " +
            "(:maxCap IS NULL OR b.capacity <= :maxCap) AND " +
            "(:minPass IS NULL OR b.nrOfPassengers >= :minPass) AND " +
            "(:maxPass IS NULL OR b.nrOfPassengers <= :maxPass)")
    Page<Bus> findByFilters(
            @Param("id") String id,
            @Param("regNum") String registrationNumber,
            @Param("status") BusStatus status,
            @Param("minCap") Integer minCapacity,
            @Param("maxCap") Integer maxCapacity,
            @Param("minPass") Integer minPassengers,
            @Param("maxPass") Integer maxPassengers,
            Pageable pageable
    );
}