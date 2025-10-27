package com.example.busmanagementsystem.model;

import java.util.List;

public class TripManager extends Staff {
    private List<DutyAssignment> assignments;
    private String employeeCode;

    public TripManager(String id, String name, String employeeCode) {
        super(id, name);
        this.employeeCode = employeeCode;
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

    public String getEmployeeCode() {
        return employeeCode;
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

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public void setAssignments(List<DutyAssignment> assignments) {
        this.assignments = assignments;
    }
    //endregion
}
