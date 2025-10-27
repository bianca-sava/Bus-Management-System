package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Driver;

import java.util.ArrayList;
import java.util.List;

public class DriverRepository implements CRUD<Driver>{

    private List<Driver> drivers;

    public DriverRepository(){
        drivers = new ArrayList<Driver>();
    }

    @Override
    public boolean create(Driver entity) {
        if(drivers.add(entity)) {
            return true;
        }

        return false;
    }

    @Override
    public List<Driver> findAll() {
        return drivers;
    }

    @Override
    public Driver findById(String id) {
        for (Driver driver : drivers) {
            if (driver.getId().equals(id)) {
                return driver;
            }
        }
        return null;
    }

    @Override
    public boolean update(String id, Driver entity) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getId().equals(id)) {
                drivers.set(i, entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getId().equals(id)) {
                drivers.remove(i);
                return true;
            }
        }
        return false;
    }
}