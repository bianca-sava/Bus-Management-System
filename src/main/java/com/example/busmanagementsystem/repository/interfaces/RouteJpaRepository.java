package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteJpaRepository extends JpaRepository<Route, String> {
    @Query(value = "SELECT r, COUNT(t) AS tripCount FROM Route r LEFT JOIN r.trips t GROUP BY r",
            countQuery = "SELECT COUNT(r) FROM Route r",
            nativeQuery = false)
    Page<Object[]> findAllSortedByTripCount(Pageable pageable);
}