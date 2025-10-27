package com.example.busmanagementsystem.model;

public abstract class Staff {
    protected String id;
    protected String name;

    public Staff(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /// Getters
    public abstract String getId();

    public abstract String getName();

    /// Setters
    public abstract void setId(String id);

    public abstract void setName(String name);

}
