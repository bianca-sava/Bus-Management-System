package com.example.busmanagementsystem.exceptions;

public class EntityNotFoundException extends RuntimeException {
    private final String fieldName;

    public EntityNotFoundException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
