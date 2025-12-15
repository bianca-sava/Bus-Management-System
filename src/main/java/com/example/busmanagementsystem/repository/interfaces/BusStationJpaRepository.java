package com.example.busmanagementsystem.repository.interfaces;

import com.example.busmanagementsystem.model.BusStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationJpaRepository extends JpaRepository<BusStation, String> {

    @Query(value = "SELECT bs, COUNT(t) AS tripCount FROM BusStation bs LEFT JOIN bs.trips t GROUP BY bs",
            countQuery = "SELECT COUNT(bs) FROM BusStation bs",
            nativeQuery = false)
    Page<Object[]> findAllSortedByTripCount(Pageable pageable);

}