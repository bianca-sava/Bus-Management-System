package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusTrip;

import java.util.ArrayList;
import java.util.List;

public class BusTripRepository implements CRUD<BusTrip> {

    List<BusTrip> busTrips;

    public BusTripRepository() {

        busTrips = new ArrayList<BusTrip>();
        BusTrip trip1 = new BusTrip("BT001", "RouteA", "2024-07-01 08:00", "2024-07-01 12:00");
        BusTrip trip2 = new BusTrip("BT002", "RouteB", "2024-07-01 09:00", "2024-07-01 13:00");
        BusTrip trip3 = new BusTrip("BT003", "RouteC", "2024-07-01 10:00", "2024-07-01 14:00");
        busTrips.add(trip1);
        busTrips.add(trip2);
        busTrips.add(trip3);
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
