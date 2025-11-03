package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.Driver;
import com.example.busmanagementsystem.model.Staff;
import com.example.busmanagementsystem.model.TripManager;
import com.example.busmanagementsystem.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StaffService {

    private StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public boolean addStaff(Staff staff){
        return staffRepository.create(staff);
    }

    public Staff getStaffById(String id){
        return staffRepository.findById(id);
    }

    public Map<String, Staff> getAllStaff(){
        return staffRepository.findAll();
    }

    public boolean updateStaff(String id, Staff staff){
        return staffRepository.update(id, staff);
    }

    public boolean deleteStaff(String id){
        return staffRepository.delete(id);
    }

    public Map<String, Staff> getAllTripManagers(){
        Map<String, Staff> result = new HashMap<>();

        for(Staff staff : staffRepository.findAll().values()){
            if(staff instanceof TripManager){
                result.put(staff.getId(), staff);
            }
        }

        return result;
    }

    public Map<String, Staff> getAllDrivers(){
        Map<String, Staff> result = new HashMap<>();

        for(Staff staff : staffRepository.findAll().values()){
            if(staff instanceof Driver){
                result.put(staff.getId(), staff);
            }
        }

        return result;
    }
}
