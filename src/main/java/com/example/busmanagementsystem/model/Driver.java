package com.example.busmanagementsystem.model;

import java.util.List;

public class Driver extends Staff {
    private List<DutyAssignment> assignments;
    private String yearsOfExperience;

    public Driver(String id, String name, String yearsOfExperience) {
        super(id, name);
        this.yearsOfExperience = yearsOfExperience;
    }


    /// Getters
    //region
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public List<DutyAssignment> getAssignments() {
        return assignments;
    }
    //endregion


    /// Setters
    //region
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setAssignments(List<DutyAssignment> assignments) {
        this.assignments = assignments;
    }
    //endregion
}
