package com.example.busmanagementsystem.repository.interfaces;

import com.example.busmanagementsystem.model.BusStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationJpaRepository extends JpaRepository<BusStation, String> {

    @Query(value = "SELECT bs, COUNT(t) AS tripCount FROM BusStation bs LEFT JOIN bs.trips t GROUP BY bs",
            countQuery = "SELECT COUNT(bs) FROM BusStation bs",
            nativeQuery = false)
    Page<Object[]> findAllSortedByTripCount(Pageable pageable);

    @Query("SELECT s FROM BusStation s WHERE " +
            "(:id IS NULL OR LOWER(s.id) LIKE LOWER(CONCAT('%', :id, '%'))) AND " +
            "(:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:city IS NULL OR LOWER(s.city) LIKE LOWER(CONCAT('%', :city, '%')))")
    Page<BusStation> findByFilters(
            @Param("id") String id,
            @Param("name") String name,
            @Param("city") String city,
            Pageable pageable
    );

}