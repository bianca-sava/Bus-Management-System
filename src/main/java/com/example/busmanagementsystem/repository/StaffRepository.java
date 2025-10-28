package com.example.busmanagementsystem.repository;

import com.example.busmanagementsystem.model.Staff;

import java.util.ArrayList;
import java.util.List;

public class StaffRepository implements CRUD<Staff> {
    private List<Staff> staffList;

    public StaffRepository() {
        staffList = new ArrayList<Staff>();
    }


    @Override
    public boolean create(Staff entity) {
        return staffList.add(entity);
    }

    @Override
    public List<Staff> findAll() {
        return staffList;
    }

    @Override
    public Staff findById(String id) {
        for (Staff staff : staffList) {
            if (staff.getId().equals(id)) {
                return staff;
            }
        }
        return null;
    }

    @Override
    public boolean update(String id, Staff entity) {
        for (int i = 0; i < staffList.size(); i++) {
            if(staffList.get(i).getId().equals(id)){
                staffList.set(i, entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        for (int i = 0; i < staffList.size(); i++) {
            if(staffList.get(i).getId().equals(id)){
                staffList.remove(i);
                return true;
            }
        }
        return false;
    }
}
