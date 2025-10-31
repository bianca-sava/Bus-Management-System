package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusStation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BusStationRepository implements CRUD<BusStation> {
    private List<BusStation> busStations;

    public BusStationRepository() {

        busStations = new ArrayList<BusStation>();
        BusStation station1 = new BusStation("BS001", "Central Station", "123 Main St");
        BusStation station2 = new BusStation("BS002", "North Station", "456 North St");
        BusStation station3 = new BusStation("BS003", "East Station", "789 East St");
        busStations.add(station1);
        busStations.add(station2);
    }

    @Override
    public boolean create(BusStation entity) {
        return busStations.add(entity);

    }

    @Override
    public List<BusStation> findAll() {
        return busStations;
    }

    @Override
    public BusStation findById(String id) {
        for (BusStation station : busStations) {
            if (station.getId().equals(id)) {
                return station;
            }
        }
        return null;

    }

    @Override
    public boolean update(String id, BusStation entity) {
        for (int i = 0; i < busStations.size(); i++) {
            if (busStations.get(i).getId().equals(id)) {
                busStations.set(i, entity);
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean delete(String id) {
        return busStations.removeIf(station -> station.getId().equals(id));
    }
}
