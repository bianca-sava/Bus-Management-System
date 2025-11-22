package com.example.busmanagementsystem.repository.interfaces;

import com.example.busmanagementsystem.model.BusTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusTripJpaRepository extends JpaRepository<BusTrip, String> {
}