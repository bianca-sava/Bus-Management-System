package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusTrip;

import java.util.ArrayList;
import java.util.List;

public class BusTripRepository implements CRUD<BusTrip> {

    List<BusTrip> busTrips;

    public BusTripRepository() {
        busTrips = new ArrayList<BusTrip>();
    }

    @Override
    public boolean create(BusTrip entity) {
        return busTrips.add(entity);
    }

    @Override
    public List<BusTrip> findAll() {
        return busTrips;
    }

    @Override
    public BusTrip findById(String id) {
        for (BusTrip busTrip : busTrips) {
            if (busTrip.getId().equals(id)) {
                return busTrip;
            }
        }
        return null;
    }

    @Override
    public boolean update(String id, BusTrip entity) {
        for (int i = 0; i < busTrips.size(); i++) {
            if (busTrips.get(i).getId().equals(id)) {
                busTrips.set(i, entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        return busTrips.removeIf(busTrip -> busTrip.getId().equals(id));
    }
}
