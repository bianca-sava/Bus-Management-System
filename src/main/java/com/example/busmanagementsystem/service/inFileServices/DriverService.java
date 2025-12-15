package com.example.busmanagementsystem.service.inFileServices;

import com.example.busmanagementsystem.model.Driver;
import com.example.busmanagementsystem.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public boolean addDriver(Driver driver){
        return driverRepository.create(driver);
    }

    public Driver getDriverById(String id){
        return driverRepository.findById(id);
    }

    public Map<String, Driver> findAllDrivers(){
        return driverRepository.findAll();
    }

    public boolean updateDriver(String id, Driver driver){
        return driverRepository.update(id, driver);
    }

    public boolean deleteDriver(String id){
        return driverRepository.delete(id);
    }
}
