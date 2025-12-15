package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteJpaRepository extends JpaRepository<Route, String> {
    @Query("SELECT r FROM Route r WHERE " +
            "(:id IS NULL OR LOWER(r.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND " +
            "(:origin IS NULL OR LOWER(r.origin.name) LIKE LOWER(CONCAT('%', :origin, '%'))) AND " +
            "(:dest IS NULL OR LOWER(r.destination.name) LIKE LOWER(CONCAT('%', :dest, '%'))) AND " +
            // Distanță
            "(:minDist IS NULL OR r.distance >= :minDist) AND " +
            "(:maxDist IS NULL OR r.distance <= :maxDist) AND " +
            // Nr. Stații (STOPS) - Adăugat înapoi
            "(:minStations IS NULL OR r.nrOfStations >= :minStations) AND " +
            "(:maxStations IS NULL OR r.nrOfStations <= :maxStations) AND " +
            // Nr. Curse
            "(:minTrips IS NULL OR SIZE(r.trips) >= :minTrips) AND " +
            "(:maxTrips IS NULL OR SIZE(r.trips) <= :maxTrips)")
    Page<Route> findByFilters(
            @Param("id") String id,
            @Param("origin") String originName,
            @Param("dest") String destinationName,
            @Param("minDist") Double minDistance,
            @Param("maxDist") Double maxDistance,
            @Param("minStations") Integer minStations,     // Param nou
            @Param("maxStations") Integer maxStations,     // Param nou
            @Param("minTrips") Integer minTrips,
            @Param("maxTrips") Integer maxTrips,
            Pageable pageable
    );
}