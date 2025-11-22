package com.example.busmanagementsystem.repository.interfaces;
import com.example.busmanagementsystem.model.Bus;
import com.example.busmanagementsystem.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteJpaRepository extends JpaRepository<Route, String> {
}