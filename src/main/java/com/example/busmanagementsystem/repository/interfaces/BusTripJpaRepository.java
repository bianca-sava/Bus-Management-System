package com.example.busmanagementsystem.repository.interfaces;

import com.example.busmanagementsystem.model.BusTrip;
import com.example.busmanagementsystem.model.BusTripStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusTripJpaRepository extends JpaRepository<BusTrip, String> {

    @Query(value = "SELECT bt, COUNT(t) AS ticketCount FROM BusTrip bt LEFT JOIN bt.tickets t GROUP BY bt",
            countQuery = "SELECT COUNT(bt) FROM BusTrip bt",
            nativeQuery = false)
    Page<Object[]> findAllSortedByTicketCount(Pageable pageable);

    @Query(value = "SELECT bt, COUNT(a) AS assignmentCount FROM BusTrip bt LEFT JOIN bt.assignments a GROUP BY bt",
            countQuery = "SELECT COUNT(bt) FROM BusTrip bt",
            nativeQuery = false)
    Page<Object[]> findAllSortedByAssignmentCount(Pageable pageable);

    @Query("SELECT t FROM BusTrip t WHERE " +
            "(:id IS NULL OR LOWER(t.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND " +
            "(:routeId IS NULL OR LOWER(t.routeId) LIKE LOWER(CONCAT('%', :routeId, '%'))) AND " +
            "(:busId IS NULL OR LOWER(t.busId) LIKE LOWER(CONCAT('%', :busId, '%'))) AND " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:minTime IS NULL OR t.startTime >= :minTime) AND " +
            "(:maxTime IS NULL OR t.startTime <= :maxTime)")
    Page<BusTrip> findByFilters(
            @Param("id") String id,
            @Param("routeId") String routeId,
            @Param("busId") String busId,
            @Param("status") BusTripStatus status,
            @Param("minTime") String minTime,
            @Param("maxTime") String maxTime,
            Pageable pageable
    );

}