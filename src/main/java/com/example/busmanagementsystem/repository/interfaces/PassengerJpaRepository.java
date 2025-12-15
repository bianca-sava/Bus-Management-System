package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerJpaRepository extends JpaRepository<Passenger, String> {
    @Query("SELECT p FROM Passenger p WHERE" +
            "(:id IS NULL OR LOWER(p.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND" +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND" +
            "(:currency IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :currency, '%'))) AND" +
            "(:minTickets IS NULL OR SIZE(p.tickets) >= :minTickets) AND" +
            "(:maxTickets IS NULL OR SIZE(p.tickets) <= :maxTickets)")
    Page<Passenger> findByFilters(
            @Param("id") String id,
            @Param("name") String name,
            @Param("currency") String currency,
            @Param("minTickets") Integer minTickets,
            @Param("maxTickets") Integer maxTickets,
            Pageable pageable
    );}