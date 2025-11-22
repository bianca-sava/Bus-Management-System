package com.example.busmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class DutyAssignment {
    private String id;
    private String tripId;
    private String staffId;
    @Enumerated(EnumType.STRING)
    private Role role;
    /// Constructors
    public DutyAssignment() {}
    public DutyAssignment(String id, String tripId, String staffId, Role role) {
        this.id = id;
        this.tripId = tripId;
        this.staffId = staffId;
        this.role = role;
    }

    public DutyAssignment(String id, String tripId, String staffId) {
        this.id = id;
        this.tripId = tripId;
        this.staffId = staffId;
        this.role = Role.RESERVE_DRIVER;
    }

    /// Getters and Setters
    // region
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    //endregion


    @Override
    public String toString() {
        return "DutyAssignment{" +
                "id='" + id + '\'' +
                ", tripId='" + tripId + '\'' +
                ", staffId='" + staffId + '\'' +
                ", role=" + role +
                '}';
    }
}
