package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.TripManager;
import com.example.busmanagementsystem.repository.TripManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TripManagerService {

    private final TripManagerRepository tripManagerRepository;

    @Autowired
    public TripManagerService(TripManagerRepository tripManagerRepository) {
        this.tripManagerRepository = tripManagerRepository;
    }

    public boolean addTripManager(TripManager tripManager){
        return tripManagerRepository.create(tripManager);
    }

    public TripManager getTripManagerById(String id){
        return tripManagerRepository.findById(id);
    }

    public Map<String, TripManager> findAllTripManagers(){
        return tripManagerRepository.findAll();
    }

    public boolean updateTripManager(String id, TripManager tripManager){
        return tripManagerRepository.update(id, tripManager);
    }

    public boolean deleteTripManager(String id){
        return tripManagerRepository.delete(id);
    }
}
