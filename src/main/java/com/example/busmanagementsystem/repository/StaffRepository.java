package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Driver;
import com.example.busmanagementsystem.model.Staff;
import com.example.busmanagementsystem.model.TripManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StaffRepository extends InMemoryRepository<Staff> {

    @Override
    protected String getIdFromEntity(Staff entity) {
        return entity.getId();
    }
}
