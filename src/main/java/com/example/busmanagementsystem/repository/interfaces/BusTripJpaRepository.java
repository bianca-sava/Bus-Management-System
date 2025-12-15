package com.example.busmanagementsystem.repository.interfaces;

import com.example.busmanagementsystem.model.BusTrip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

}