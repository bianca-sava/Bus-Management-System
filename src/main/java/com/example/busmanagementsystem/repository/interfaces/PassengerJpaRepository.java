package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerJpaRepository extends JpaRepository<Passenger, String> {
    @Query(value = "SELECT p, COUNT(t) AS ticketCount FROM Passenger p LEFT JOIN p.tickets t GROUP BY p",
            countQuery = "SELECT COUNT(p) FROM Passenger p",
            nativeQuery = false)
    Page<Object[]> findAllSortedByTicketCount(Pageable pageable);
}