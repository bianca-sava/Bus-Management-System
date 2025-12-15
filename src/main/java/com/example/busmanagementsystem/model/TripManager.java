package com.example.busmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trip_managers")
public class TripManager extends Staff {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_manager_id")
    private List<DutyAssignment> assignments;

    @NotBlank(message = "The Employee Code can't be blank")
    @Column(unique = true)
    private String employeeCode;

    public TripManager() {
        super();
        assignments = new ArrayList<DutyAssignment>();
    }

    public TripManager(String id, String name, String employeeCode) {
        super(id, name);
        this.employeeCode = employeeCode;
        assignments = new ArrayList<DutyAssignment>();
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
