package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketJpaRepository extends JpaRepository<Ticket, String> {
    @Query("SELECT t FROM Ticket t WHERE " +
            "(:id IS NULL OR LOWER(t.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND " +
            "(:tripId IS NULL OR LOWER(t.tripId) LIKE LOWER(CONCAT('%', :tripId, '%'))) AND " +
            "(:passengerId IS NULL OR LOWER(t.passengerId) LIKE LOWER(CONCAT('%', :passengerId, '%'))) AND " +
            "(:seatNumber IS NULL OR LOWER(t.seatNumber) LIKE LOWER(CONCAT('%', :seatNumber, '%'))) AND " +
            "(:minPrice IS NULL OR t.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR t.price <= :maxPrice) AND " +
            "(:checkedIn IS NULL OR t.checkedIn = :checkedIn)")
    Page<Ticket> findByFilters(
            @Param("id") String id,
            @Param("tripId") String tripId,
            @Param("passengerId") String passengerId,
            @Param("seatNumber") String seatNumber,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("checkedIn") Boolean checkedIn,
            Pageable pageable
    );
}