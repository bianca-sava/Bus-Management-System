package com.example.busmanagementsystem.model;


public class DutyAssignment {
    private String id;
    private String tripId;
    private String staffId;
    private Role role;
    /// Constructors
    public DutyAssignment(String id, String tripId, String staffId, Role role) {
        this.id = id;
        this.tripId = tripId;
        this.staffId = staffId;
        this.role = role;
    }
    /// If no role is specified, default to RESERVE_DRIVER
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
