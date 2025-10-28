package com.example.busmanagementsystem.model;

public enum BusTripStatus {
    PLANNED("This trip is planned"),
    ACTIVE("This trip is active"),
    COMPLETED("This trip was completed");

    private final String description;

    BusTripStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
