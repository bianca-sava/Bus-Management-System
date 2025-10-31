package com.example.busmanagementsystem.model;

public enum BusStatus {
    DOWN("Down"),
    ACTIVE("Active");

    private final String description;

    BusStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
