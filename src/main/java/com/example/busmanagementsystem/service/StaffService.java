package com.example.busmanagementsystem.service;

import com.example.busmanagementsystem.model.Staff;
import com.example.busmanagementsystem.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
