package com.example.busmanagementsystem.model;

public enum Role {
    PRIMARY_DRIVER("Primary Driver"),
    RESERVE_DRIVER("Reserve  Driver");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
