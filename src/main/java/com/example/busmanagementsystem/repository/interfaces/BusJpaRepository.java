package com.example.busmanagementsystem.repository.interfaces;

import com.example.busmanagementsystem.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusJpaRepository extends JpaRepository<Bus, String> {
    boolean existsByRegistrationNumber(String registrationNumber);
}