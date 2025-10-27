package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.Bus;

import java.util.List;

public class BusRepository implements CRUD<Bus> {

    private List<Bus> buses;

    @Override
    public boolean create(Bus entity) {
        return buses.add(entity);
    }

    @Override
    public List<Bus> findAll() {
        return buses;
    }

    @Override
    public Bus findById(String id) {
        for (Bus bus : buses) {
            if (bus.getId().equals(id)) {
                return bus;
            }
        }
        return null;

    }

    @Override
    public boolean update(String id, Bus entity) {
        for (int i = 0; i < buses.size(); i++) {
            if (buses.get(i).getId().equals(id)) {
                buses.set(i, entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return buses.removeIf(entity -> entity.getId().equals(id));
    }
}
