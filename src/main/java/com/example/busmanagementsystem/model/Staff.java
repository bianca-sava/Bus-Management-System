package com.example.busmanagementsystem.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;

@MappedSuperclass
public abstract class Staff {
    @Id
    @NotBlank(message = "The Id can't be blank")
    protected String id;

    @NotBlank(message = "You must introduce the name")
    protected String name;

    public Staff(){

    }

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
