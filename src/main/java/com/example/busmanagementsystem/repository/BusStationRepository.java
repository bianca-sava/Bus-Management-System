package com.example.busmanagementsystem.repository;
import com.example.busmanagementsystem.model.BusStation;
import java.util.List;

public class BusStationRepository implements CRUD<BusStation> {
    private List<BusStation> busStations;

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
