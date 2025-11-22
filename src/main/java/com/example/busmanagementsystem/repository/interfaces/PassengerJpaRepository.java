package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.Passenger;
import com.example.busmanagementsystem.repository.PassengerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerJpaRepository extends JpaRepository<Passenger, String> {
}