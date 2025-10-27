package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.TripManager;

import java.util.List;

public class TripManagerRepository implements CRUD<TripManager> {
    List<TripManager> tripManagers;

    @Override
    public boolean create(TripManager entity) {
        if(tripManagers.add(entity)){
            return true;
        }
        return false;
    }

    @Override
    public List<TripManager> findAll() {
        return tripManagers;
    }

    @Override
    public TripManager findById(String id) {
        for(TripManager tripManager : tripManagers){
            if(tripManager.getId().equals(id)){
                return tripManager;
            }
        }
        return null;
    }

    @Override
    public boolean update(String id, TripManager entity) {
        for(int i = 0; i < tripManagers.size(); i++){
            if(tripManagers.get(i).getId().equals(id)){
                tripManagers.set(i, entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for(int i = 0; i < tripManagers.size(); i++){
            if(tripManagers.get(i).getId().equals(id)){
                tripManagers.remove(i);
                return true;
            }
        }
        return false;
    }
}